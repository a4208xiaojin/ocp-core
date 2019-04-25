package com.nbd.ocp.core.exception.service;
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


import com.nbd.ocp.core.exception.service.code.ServiceExceptionCodeConstant;

public class BusiException extends ServiceException {
    public BusiException(Throwable t,String responseMsg) {
        super(t,responseMsg);
    }
    public BusiException(String message) {
        super(message);
    }

    public BusiException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    public BusiException(String code,String message, String responseMsg) {
        super(message, responseMsg);
        setCode(code);
    }
    @Override
    public String errorCode() {
        return ServiceExceptionCodeConstant.DEFAULT_BUSI;
    }
}
