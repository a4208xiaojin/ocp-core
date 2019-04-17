package com.nbd.ocp.core.repository.properties;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jhb
 */
public  class BaseApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(BaseApplicationProperties.class);
    private static  volatile Properties properties =new Properties();

    private static volatile BaseApplicationProperties instance;

    private BaseApplicationProperties() {
        try {
            InputStream inputStream= BaseApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("加载文件失败：",e.getStackTrace());
        }
    }

    public static BaseApplicationProperties getInstance() {
        if (instance == null) {
            synchronized (BaseApplicationProperties.class) {
                if (instance == null) {
                    instance = new BaseApplicationProperties();
                }
            }

        }
        return instance;
    }
    public Properties getProperties(){
        return properties;
    }

    public String get(String key){
        String value=properties.getProperty(key);
        if(StringUtils.isEmpty(value)){
            throw  new  NullPointerException("application.properties中，'"+key+"'未设值");
        }else{
            logger.debug("找到{}的值为{}",key,value);
            return value;
        }

    }

}
