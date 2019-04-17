package com.nbd.ocp.core.repository.tree;
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

import com.nbd.ocp.core.repository.base.IOcpBaseController;
import com.nbd.ocp.core.repository.response.OcpJsonResponse;
import com.nbd.ocp.core.repository.tree.request.OcpTreeOcpQueryBaseVo;
import com.nbd.ocp.core.repository.utils.OcpGenericsUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author jhb
 */
public interface IOcpTreeBaseController<T extends IOcpTreeBaseDo,I extends IOcpTreeBaseService>  extends IOcpBaseController<T,I> {

    Logger logger = LoggerFactory.getLogger(IOcpTreeBaseController.class);

    default I getTreeBaseService(){
        return (I) OcpSpringUtil.getBean(OcpGenericsUtils.getControllerSuperClassGenericsType(getClass(), IOcpTreeBaseService.class));
    }

    @RequestMapping(value = "/list-tree", method = RequestMethod.GET)
    @ResponseBody
    default OcpJsonResponse listTree(OcpTreeOcpQueryBaseVo treeQueryBaseVo) {
        try {
            List<T> r =getTreeBaseService().listTree(treeQueryBaseVo);
            return OcpJsonResponse.success("查询成功",r);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return OcpJsonResponse.failed();
        }
    }



}
