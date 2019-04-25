package com.nbd.ocp.core.exception.system;
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


import com.nbd.ocp.core.exception.system.code.SystemExceptionCodeConstant;

public class SysNullDataException extends SystemException {
    public SysNullDataException(Throwable t, String responseMsg) {
        super(t,responseMsg);
    }

    public SysNullDataException(String message) {
        super(message);
    }

    public SysNullDataException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    @Override
    public String errorCode() {
        return SystemExceptionCodeConstant.NULL;
    }
}
