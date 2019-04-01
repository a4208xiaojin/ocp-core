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


import com.nbd.ocp.core.repository.exception.service.code.ServiceExceptionCodeConstant;

public class ExistsDataException extends ServiceException {

    public ExistsDataException(Throwable t,String responseMsg) {
        super(t,responseMsg);
    }
    public ExistsDataException(String message) {
        super(message);
    }
    public ExistsDataException(String message, String responseMsg) {
        super(message, responseMsg);
    }
    @Override
    public String errorCode() {
        return ServiceExceptionCodeConstant.EXISTS_DATA;
    }
}
