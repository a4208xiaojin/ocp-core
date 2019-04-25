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


import com.nbd.ocp.core.exception.system.SystemException;
import com.nbd.ocp.core.exception.system.code.SystemExceptionCodeConstant;

/**
 * @author jin
 */
public class SysNoSuchFieldException extends SystemException {
    public SysNoSuchFieldException(Throwable t, String responseMsg) {
        super(t,responseMsg);
    }
    public SysNoSuchFieldException(String message) {
        super(message);
    }

    public SysNoSuchFieldException(String message, String responseMsg) {
        super(message, responseMsg);
    }

    public SysNoSuchFieldException(String code, String message, String responseMsg) {
        super(message, responseMsg);
        setCode(code);
    }
    @Override
    public String errorCode() {
        return SystemExceptionCodeConstant.NO_SUCH_FIELD;
    }
}
