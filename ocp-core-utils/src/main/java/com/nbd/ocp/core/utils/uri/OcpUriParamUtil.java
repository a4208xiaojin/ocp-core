package com.nbd.ocp.core.utils.uri;
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


import com.alibaba.fastjson.JSON;
import com.nbd.ocp.core.utils.reflect.OcpReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jin
 */

public class OcpUriParamUtil {
    private static Logger logger = LoggerFactory.getLogger(OcpUriParamUtil.class);
    public static String bean2UrlParamStr(Object obj) {
        Map<String,String> map=bean2UrlParamsMap(obj,null);
        if (map == null||map.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(convertKey(entry.getKey())).append("=").append(entry.getValue());
            sb.append("&");
        }
        String s = sb.substring(0,(sb.length()-1));
        return s;
    }

    private static Map<String,String> bean2UrlParamsMap(Object obj, String parentName) throws IllegalArgumentException {
        System.out.println(JSON.toJSONString(obj));
        parentName=parentName==null?"":parentName;
        Map<String,String> map= new LinkedHashMap<>();
        if(OcpReflectUtil.isPrimitive(obj)){
            map.put(parentName,String.valueOf(obj));
            return map;
        }else if(List.class.isAssignableFrom(obj.getClass())|| Set.class.isAssignableFrom(obj.getClass())){
            List listObject=(List)obj;
            for(int i=0;i<listObject.size();i++){
                Object objListItem=listObject.get(i);
                map.putAll(bean2UrlParamsMap(objListItem,parentName+"[${"+i+"}]"));
            }
            return map;
        }else if(Map.class.isAssignableFrom(obj.getClass())){
            Map mapField=(Map)obj;
            for(Object objMapKey : mapField.keySet()){
                Object key=objMapKey;
                Object value= mapField.get(key);
                map.putAll(bean2UrlParamsMap(value,parentName+"["+key+"]"));
            }
            return map;
        }
        //反射获取对象所有属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //进行遍历
        for (Field field : fields) {
            field.setAccessible(true);
            //获取属性名字
            String name = field.getName();
            //获取属性类型
            Class fieldClass = field.getType();
            Object fieldValue=null;
            try {
                fieldValue=field.get(obj);
                if(fieldValue==null){
                    continue;
                }

            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(),e);
                continue;
            }
            //判断类是否为jdk自带
            if(OcpReflectUtil.isJdkClass(fieldClass)){
                if(fieldClass.isAssignableFrom(List.class)|| fieldClass.isAssignableFrom(Set.class)){
                    String keyPre= StringUtils.isEmpty(parentName)?name:(parentName+"."+name);
                    List listFieldValue=(List)fieldValue;
                    for(int i=0;i<listFieldValue.size();i++){
                        Object objListItem=listFieldValue.get(i);
                        map.putAll(bean2UrlParamsMap(objListItem,keyPre+"[${"+i+"}]"));
                    }
                }else if(fieldClass.isAssignableFrom(Map.class)){
                    Map mapField=(Map)fieldValue;
                    String keyPre= StringUtils.isEmpty(parentName)?name:(parentName+"."+name);
                    for(Object objMapKey : mapField.keySet()){
                        Object key=objMapKey;
                        Object value= mapField.get(key);
                        map.putAll(bean2UrlParamsMap(value,keyPre+"["+key+"]"));
                    }
                }
            }else{
                String keyPre= StringUtils.isEmpty(parentName)?name:(parentName+"."+name);
                map.putAll(bean2UrlParamsMap(fieldValue,keyPre));
            }
        }
        return map;
    }
    private static String convertKey(String key){
        String result=key.replaceAll("\\[\\$\\{[0-9]\\}\\]","");
        return result;
    }

}
