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

/**
 * @author jin
 */
public class IllegalAccessException extends SystemException {
    public IllegalAccessException(Throwable t,String responseMsg) {
        super(t,responseMsg);
    }
    public IllegalAccessException(String message) {
        super(message);
    }

    public IllegalAccessException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    public IllegalAccessException(String code,String message, String responseMsg) {
        super(message, responseMsg);
        setCode(code);
    }
    @Override
    public String errorCode() {
        return SystemExceptionCodeConstant.ILLEGAL_ACCESS;
    }
}
