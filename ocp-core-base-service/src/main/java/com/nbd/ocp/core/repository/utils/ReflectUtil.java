package com.nbd.ocp.core.repository.utils;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ReflectUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);
    /**
     * 判断类是否为jdk自带
     * @param clz
     * @return
     */
    public static boolean isJdkClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    /**判断一个对象是否是基本类型或基本类型的封装类型*/
    public static boolean isPrimitive(Object obj) {
        try {
            return obj.getClass().isPrimitive()||
                    obj.getClass().isAssignableFrom(String.class)||
                    obj.getClass().getField("TYPE").get(null).getClass().isPrimitive();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
    public static Object getFieldValue(Object obj, String fieldName){
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        }catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            return null;
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

}
