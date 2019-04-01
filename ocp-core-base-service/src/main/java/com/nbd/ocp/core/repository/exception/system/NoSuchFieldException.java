package com.nbd.ocp.core.repository.exception.system;
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


import com.nbd.base.exception.system.code.SystemExceptionCodeConstant;

/**
 * @author jin
 */
public class NoSuchFieldException extends SystemException {
    public NoSuchFieldException(Throwable t, String responseMsg) {
        super(t,responseMsg);
    }
    public NoSuchFieldException(String message) {
        super(message);
    }

    public NoSuchFieldException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    public NoSuchFieldException(String code, String message, String responseMsg) {
        super(message, responseMsg);
        setCode(code);
    }
    @Override
    public String errorCode() {
        return SystemExceptionCodeConstant.NO_SUCH_FIELD;
    }
}
