package com.nbd.ocp.core.exception.system;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 系统内部异常,baseService中使用，不需要再方法中写throws。
 * @link 具体业务中请使用 com.nbd.base.exception.service.ServiceException
 * @author jhb
 */
public abstract class SystemException extends RuntimeException {

    private static final long serialVersionUID = -4742832112872227456L;
    /**系统错误码*/
    private String code;
    /**外部错误码*/
    private String responseMsg;

    private SystemException() {
        super();
        this.code=errorCode();
    }
    public SystemException(Throwable t, String responseMsg) {
        super(t);
        this.code=errorCode();
        this.responseMsg = responseMsg;
    }

    public SystemException(String message) {
        super(message);
        this.code=errorCode();
        this.responseMsg = message;
    }
    public SystemException(String message, String responseMsg) {
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