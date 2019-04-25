package com.nbd.ocp.core.context.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dongguabai on 2018-06-22 14:44
 */
@Component
@Lazy(false)
@Order(1)
public class OcpSpringUtil implements ApplicationContextAware {
    private static Logger             logger = LoggerFactory.getLogger(OcpSpringUtil.class);
    private static ApplicationContext applicationContext;
 
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    public static Object getBean(String name) {
        try {
            Class clazz=Class.forName(name);
            Map<String, Object>  clazzMap=getApplicationContext().getBeansOfType(clazz);
            if(clazzMap!=null&&clazzMap.size()==1){
                return clazzMap.values().iterator().next();
            }else if(clazzMap.size()>1){
                List<String> clazzNames=new ArrayList<>();
                for(Map.Entry<String,Object> entry: clazzMap.entrySet()){
                    clazzNames.add(entry.getValue().getClass().getName());

                }
                throw new NoUniqueBeanDefinitionException(clazz,clazzNames);
            }else{
                return null;
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
 
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
 
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String,T> getBeansOfType(Class<T> clazz) {
        Map<String,T> map=new HashMap<>();
        Map<String,T> beans=getApplicationContext().getBeansOfType(clazz);
        if(beans!=null){
            map.putAll(beans);
        }
        return map;
    }
    public static <T> List<T> getBeansOfTypeList(Class<T> clazz) {
        Map<String,T> map=getBeansOfType(clazz);
        List<T> list=new ArrayList<>();
        for(T t:map.values()){
            list.add(t);
        }
        return list;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (OcpSpringUtil.applicationContext == null) {
            OcpSpringUtil.applicationContext = applicationContext;
        }
    }
 
    private OcpSpringUtil() {
    }
}