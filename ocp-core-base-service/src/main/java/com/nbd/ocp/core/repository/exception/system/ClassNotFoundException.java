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
public class ClassNotFoundException extends SystemException {
    public ClassNotFoundException(Throwable t,String responseMsg) {
        super(t,responseMsg);
    }
    public ClassNotFoundException(String message) {
        super(message);
    }

    public ClassNotFoundException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    public ClassNotFoundException(String code,String message, String responseMsg) {
        super(message, responseMsg);
        setCode(code);
    }
    @Override
    public String errorCode() {
        return SystemExceptionCodeConstant.CLASS_NO_FOUND;
    }
}
