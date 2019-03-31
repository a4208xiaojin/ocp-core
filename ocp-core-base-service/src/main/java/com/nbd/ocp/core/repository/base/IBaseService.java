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


import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.CurrentTenant;
import com.nbd.ocp.core.repository.utils.GenericsUtils;
import com.nbd.ocp.core.repository.utils.SpringUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author jhb
 */
@Transactional(readOnly = true)
@CurrentTenant
public interface IBaseService<T extends BaseDo,I extends IBaseDao> {
    default I getBaseDAO(){
        return (I) SpringUtil.getBean(GenericsUtils.getSuperClassGenericsType(getClass(), IBaseDao.class));
    }
    default List<T> getA() {
        return getBaseDAO().getA();
    }
}
