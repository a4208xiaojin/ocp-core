package com.nbd.ocp.core.context.threadlocal;
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



import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jin
 */

public class InvocationInfoProxy {
    private static final ThreadLocal<InvocationInfo> threadLocal = ThreadLocal.withInitial(() -> new InvocationInfo());

    private InvocationInfoProxy() {
    }


    static class InvocationInfo extends HashMap<String,String> implements Serializable {
        static final String USER_NAME_KEY="user_name";
        static final String TOKEN_KEY="token";
        static final String TENANT_ID_KEY="tenantId";
        static final String SYS_ID_KEY="sysId";
        static final String CLIENT_ID_KEY="client_id";
        static final String USER_ID_KEY="userId";

        InvocationInfo() {
        }
    }

    public static String getUserName(){
        return threadLocal.get().get(InvocationInfo.USER_NAME_KEY);
    }
    public static void setUserName(String userName){
        threadLocal.get().put(InvocationInfo.USER_NAME_KEY,userName);
    }

    public static String getToken(){
        return threadLocal.get().get(InvocationInfo.TOKEN_KEY);
    }
    public static void setToken(String token){
        threadLocal.get().put(InvocationInfo.TOKEN_KEY,token);
    }

    public static String getTenantId(){
        return threadLocal.get().get(InvocationInfo.TENANT_ID_KEY);
    }
    public static void setTenantId(String tenantId){
        threadLocal.get().put(InvocationInfo.TENANT_ID_KEY,tenantId);
    }

    public static String getSysId(){
        return threadLocal.get().get(InvocationInfo.SYS_ID_KEY);
    }
    public static void setSysId(String sysId){
        threadLocal.get().put(InvocationInfo.SYS_ID_KEY,sysId);
    }

    public static String getClientId(){
        return threadLocal.get().get(InvocationInfo.CLIENT_ID_KEY);
    }
    public static void setClientId(String clientId){
        threadLocal.get().put(InvocationInfo.CLIENT_ID_KEY,clientId);
    }

    public static String getUserId(){
        return threadLocal.get().get(InvocationInfo.USER_ID_KEY);
    }
    public static void setUserId(String userId){
        threadLocal.get().put(InvocationInfo.USER_ID_KEY,userId);
    }

    public static void putAll(Map<String,String> parameters){
        threadLocal.get().putAll(parameters);
    }

    public static Set<Map.Entry<String, String>> getAll() {
        return threadLocal.get().entrySet();
    }

    public static void init(HttpServletRequest httpServletRequest) {
        InvocationInfoUtil.setTokenInfo(httpServletRequest);

    }
    public static void reset() {
        threadLocal.remove();
    }

}
