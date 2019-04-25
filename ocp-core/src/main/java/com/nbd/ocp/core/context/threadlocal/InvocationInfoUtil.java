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


import com.nbd.ocp.core.context.properties.BaseApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author jin
 */

public class InvocationInfoUtil {

    private static final Logger logger=LoggerFactory.getLogger(InvocationInfoUtil.class);
    private static final String AUTHORIZATION_KEY="Authorization";
    private static final String AUTHORIZATION_PRE_KEY="bearer_";
    private static final String SECURITY_OAUTH2_JWT_SIGNIN_GKEY="org.jhb.nbd.security.oauth2.jwtSigningKey";


    public static void setTokenInfo(HttpServletRequest request) {
        String authorization=request.getHeader(AUTHORIZATION_KEY);
        if(StringUtils.isEmpty(authorization)||authorization.length()<AUTHORIZATION_PRE_KEY.length()){
            return;
        }
        String token=authorization.substring(AUTHORIZATION_PRE_KEY.length());
        String jwtKey= BaseApplicationProperties.getInstance().get(SECURITY_OAUTH2_JWT_SIGNIN_GKEY);
        Claims claims= null;
        try {
            claims = Jwts.parser().setSigningKey(jwtKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            logger.error("Headers:Authorization中token信息错误");
        }

        InvocationInfoProxy.setToken(token);
        InvocationInfoProxy.setTenantId(String.valueOf(claims.get(InvocationInfoProxy.InvocationInfo.TENANT_ID_KEY)));
        InvocationInfoProxy.setSysId(String.valueOf(claims.get(InvocationInfoProxy.InvocationInfo.SYS_ID_KEY)));
        InvocationInfoProxy.setUserName(String.valueOf(claims.get(InvocationInfoProxy.InvocationInfo.USER_NAME_KEY)));
        InvocationInfoProxy.setClientId(String.valueOf(claims.get(InvocationInfoProxy.InvocationInfo.CLIENT_ID_KEY)));
        InvocationInfoProxy.setUserId(String.valueOf(claims.get(InvocationInfoProxy.InvocationInfo.USER_ID_KEY)));

    }
}
