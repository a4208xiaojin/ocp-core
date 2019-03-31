package com.nbd.ocp.core.jpa.service.impl;
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


import com.nbd.ocp.core.jpa.dao.IUserDao;
import com.nbd.ocp.core.jpa.dao.UserDo;
import com.nbd.ocp.core.jpa.service.IUserService;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.CurrentTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jin
 */
@Service
@Transactional(readOnly = true)
@CurrentTenant
public class UserServiceImpl implements IUserService{
    @Autowired
    IUserDao userDao;
    @Override
    public List<UserDo> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public UserDo save(UserDo userDO) {
        return userDao.save(userDO);
    }


}
