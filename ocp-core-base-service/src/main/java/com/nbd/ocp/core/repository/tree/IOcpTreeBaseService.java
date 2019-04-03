package com.nbd.ocp.core.repository.tree;
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

import com.nbd.ocp.core.repository.base.IOcpBaseService;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant;
import com.nbd.ocp.core.repository.tree.request.OcpTreeQueryBaseVo;
import java.util.List;


/**
 * @author jhb
 */
@org.springframework.transaction.annotation.Transactional(readOnly = true,rollbackFor = Exception.class)
@OcpCurrentTenant
public  interface IOcpTreeBaseService<T extends IOcpTreeBaseDo,I extends IOcpTreeBaseDao> extends IOcpBaseService<T,I> {
    String getMaxInnerCode();

    void moveAllChildren(String cascadelInnerCode, String cascadelInnerCodeParent);

    boolean hasChildren(String cascadelInnerCodeParent);

    List<T> treeAllChildrenBysCascade(String cascadelInnerCodeParent);


    List<T> listTree(OcpTreeQueryBaseVo treeQueryBaseVo);
}
