package com.nbd.ocp.core.repository.exception.service;
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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 系统对外接口异常，业务处理异常，在具体业务中使用，需要再方法中写throws。
 */
public abstract class ServiceException extends Exception {
    private static final long serialVersionUID = -4742832112872227456L;
    /**系统错误码*/
    private String code;
    /**外部错误码*/
    private String responseMsg;

    private ServiceException() {
        super();
        this.code=errorCode();
    }
    public ServiceException(Throwable t,String responseMsg) {
        super(t);
        this.code=errorCode();
        this.responseMsg = responseMsg;
    }

    public ServiceException(String message) {
        super(message);
        this.code=errorCode();
        this.responseMsg = message;
    }
    public ServiceException( String message, String responseMsg) {
        super(message);
        this.code=errorCode();
        this.responseMsg = responseMsg;
    }


    public abstract String errorCode();

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getResponseMsg() {
        return responseMsg;
    }
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
