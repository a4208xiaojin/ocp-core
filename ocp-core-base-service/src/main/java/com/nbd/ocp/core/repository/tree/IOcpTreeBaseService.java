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
import com.nbd.ocp.core.repository.exception.service.ExistsDataException;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant;
import com.nbd.ocp.core.repository.request.QueryPageBaseVo;
import com.nbd.ocp.core.repository.tree.request.OcpTreeQueryBaseVo;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * @author jhb
 */
@org.springframework.transaction.annotation.Transactional(readOnly = true,rollbackFor = Exception.class)
@OcpCurrentTenant
public  interface IOcpTreeBaseService<T extends IOcpTreeBaseDo,I extends IOcpTreeBaseDao> extends IOcpBaseService<T,I> {


    T save(T treeBaseDo) throws ExistsDataException;

    List<T> saveAll(List<T> list) throws ExistsDataException;

    T updateSelective(T treeBaseDo);

    List<T> treeAllChildrenBysCascade(String cascadeInnerCodeParent);

    List<T> listTree(OcpTreeQueryBaseVo treeQueryBaseVo);

    void deleteById(String id);

    void moveAllChildren(String cascadeInnerCode, String cascadelInnerCodeParent);

    boolean hasChildren(String cascadeInnerCodeParent);

    String getMaxInnerCode();

    T getById(String id);


    Page<T> page(QueryPageBaseVo queryPageBaseVo);

    List<T> list( QueryPageBaseVo queryBaseVo);


}
