package com.nbd.ocp.core.jpa.service;
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


import com.nbd.ocp.core.jpa.dao.IOcpUserDao;
import com.nbd.ocp.core.jpa.dao.OcpUserDo;
import com.nbd.ocp.core.repository.base.IOcpBaseService;

import java.util.List;

/**
 * @author jin
 */
public interface IOcpUserService extends IOcpBaseService<OcpUserDo, IOcpUserDao> {
    List<OcpUserDo> findAll();

    OcpUserDo save(OcpUserDo userDO);

    List<OcpUserDo> listUsers();
}
