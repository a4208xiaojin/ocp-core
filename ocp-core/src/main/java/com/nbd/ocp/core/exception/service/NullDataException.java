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

public class NullDataException extends ServiceException {
    public NullDataException(Throwable t,String responseMsg) {
        super(t,responseMsg);
    }

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    @Override
    public String errorCode() {
        return ServiceExceptionCodeConstant.NULL;
    }
}
