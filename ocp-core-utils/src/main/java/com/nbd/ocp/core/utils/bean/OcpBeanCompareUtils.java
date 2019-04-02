package com.nbd.ocp.core.utils.bean;
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


import java.lang.reflect.Field;

/**
 * @author jhb
 */
public class OcpBeanCompareUtils {

    /**
     * 复制对象属性，sourceBean 属性复制到 targetBean ，sourceBean存在值则以sourceBean为准，targetBean为默认值。
     * @param sourceBean 源对象
     * @param targetBean 目标对象
     * @param <T> 对象类型
     * @return 合并后的对象
     */

    public static  <T> T combineSydwCore(T sourceBean,T targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for(int i=0; i<sourceFields.length; i++){
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if( sourceField.get(sourceBean) != null ){
                    targetField.set(targetBean,sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targetBean;
    }
}
