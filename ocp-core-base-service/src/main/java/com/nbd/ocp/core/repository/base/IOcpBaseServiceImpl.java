package com.nbd.ocp.core.repository.base;

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


import com.nbd.ocp.core.repository.utils.OcpGenericsUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;


/**
 * @author jhb
 */
public interface IOcpBaseServiceImpl<T extends IOcpBaseDo,I extends IOcpBaseDao> extends IOcpBaseService<T,I> {
    default I getBaseDao(){
        return (I) OcpSpringUtil.getBean(OcpGenericsUtils.getDaoSuperClassGenericsType(getClass(), IOcpBaseDao.class));
    }
}
