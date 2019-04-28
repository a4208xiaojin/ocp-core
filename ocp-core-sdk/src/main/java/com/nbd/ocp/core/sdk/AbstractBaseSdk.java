package com.nbd.ocp.core.sdk;
/*
                       _ooOoo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      O\  =  /O
                   ____/`---'\____
                 .'  \\|     |//  `.
                /  \\|||  :  |||//  \
               /  _||||| -:- |||||-  \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |   |
               \  .-\__  `-`  ___/-. /
             ___`. .'  /--.--\  `. . __
          ."" '<  `.___\_<|>_/___.'  >'"".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `-.   \_ __\ /__ _/   .-` /  /
    ======`-.____`-.___\_____/___.-`____.-'======
                       `=---='
    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
             佛祖保佑       永无BUG
*/


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbd.ocp.core.context.properties.BaseApplicationProperties;
import com.nbd.ocp.core.request.OcpQueryPageBaseVo;
import com.nbd.ocp.core.response.OcpJsonResponse;
import com.nbd.ocp.core.utils.uri.OcpUriParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author jin
 */

public class AbstractBaseSdk  implements  IBaseSdk{
    Logger logger= LoggerFactory.getLogger(IBaseSdk.class);

    BaseApplicationProperties baseApplicationProperties=BaseApplicationProperties.getInstance();

    public String getUrl(String rootUrl, String resourceUri, Map<String,String> parameters){
        StringBuffer absoluteUrl=new StringBuffer(baseApplicationProperties.get(rootUrl));
        absoluteUrl.append("/").append(resourceUri);
        if(parameters!=null&&!parameters.isEmpty()){
            absoluteUrl.append("?");
            for(Map.Entry<String,String> entry:parameters.entrySet()){
                absoluteUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return absoluteUrl.toString();
    }


    public String getUrl(String rootUrl,Map<String,Object> parameters, String... url){
        StringBuffer absoluteUrl=new StringBuffer(baseApplicationProperties.get(rootUrl));
        for(String path:url){
            absoluteUrl.append("/").append(path);
        }

        OcpQueryPageBaseVo pageBaseVo =new OcpQueryPageBaseVo();
        pageBaseVo.setParameters(parameters);
        String  paramStr= OcpUriParamUtil.bean2UrlParamStr(pageBaseVo);
        absoluteUrl.append("?").append(paramStr);
        return absoluteUrl.toString();
    }

    public <T> OcpJsonResponse<?> executeResponse(ResponseEntity<String> responseEntity, Class<T> clzz){

        if(HttpStatus.OK.value()==responseEntity.getStatusCodeValue()){
            OcpJsonResponse jsonResponse= JSONObject.parseObject(responseEntity.getBody(),OcpJsonResponse.class);
            Object data=jsonResponse.getData();
            if(data instanceof JSONArray){
                JSONArray jsonArray= (JSONArray) data;
                List<T> dataResult=jsonArray.toJavaList(clzz);
                jsonResponse.setData(dataResult);
            }else if(data instanceof JSONObject){
                JSONObject jsonObject= (JSONObject) data;
                T t=jsonObject.toJavaObject(clzz);
                jsonResponse.setData(t);
            }
            return jsonResponse;

        }else{
            return OcpJsonResponse.failed();
        }
    }
}
