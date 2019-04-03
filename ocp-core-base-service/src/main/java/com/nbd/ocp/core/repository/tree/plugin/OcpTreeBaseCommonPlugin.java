package com.nbd.ocp.core.repository.tree.plugin;
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


import com.nbd.ocp.core.repository.base.plugin.OcpPluginSupportUtil;
import com.nbd.ocp.core.repository.crud.plugin.IOcpBaseCrudCommonPlugin;
import com.nbd.ocp.core.repository.request.QueryPageBaseVo;
import com.nbd.ocp.core.repository.tree.IOcpTreeBaseDao;
import com.nbd.ocp.core.repository.tree.IOcpTreeBaseDo;
import com.nbd.ocp.core.repository.tree.IOcpTreeBaseService;
import com.nbd.ocp.core.repository.tree.constant.OcpTreeBaseConstract;
import com.nbd.ocp.core.repository.tree.exception.TreeException;
import com.nbd.ocp.core.repository.tree.utils.OcpInnerCodeUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于crud的树组件处理类
 * @author jhb
 */
public  class OcpTreeBaseCommonPlugin implements IOcpBaseCrudCommonPlugin<IOcpTreeBaseDo, IOcpTreeBaseDao> {

    private IOcpTreeBaseService treeBaseService;

    private String innerCodeKey;
    @Override
    public boolean support(Class<?> c) {
        if(OcpPluginSupportUtil.support(c, IOcpTreeBaseDo.class)){
            Object obj= OcpSpringUtil.getBean(c);
            innerCodeKey=OcpPluginSupportUtil.getBaseDoName(c, IOcpTreeBaseDo.class);
            treeBaseService= (IOcpTreeBaseService) obj;
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void beforeSave(IOcpTreeBaseDao treeBaseDao, IOcpTreeBaseDo treeBaseDo) {
        /** 获取内部码 */
        treeBaseDo.setInnerCode(OcpInnerCodeUtils.getRedisKey(innerCodeKey,treeBaseService));
        String cascadeInnerCode;
        /**根节点为虚拟节点默认设置为root*/
        if(StringUtils.isEmpty(treeBaseDo.getPid())|| OcpTreeBaseConstract.TREE_ROOT_ID.equals(treeBaseDo.getPid())){
            treeBaseDo.setPid(OcpTreeBaseConstract.TREE_ROOT_ID);
            cascadeInnerCode=treeBaseDo.getInnerCode();
        }else{
            IOcpTreeBaseDo treeBaseDoParent = (IOcpTreeBaseDo) treeBaseDao.findById(treeBaseDo.getPid()).get();
            if(treeBaseDoParent!=null){
                cascadeInnerCode=treeBaseDoParent.getCascadeInnerCode()+treeBaseDo.getInnerCode();
            }else {
                /** 父节点不存在抛出异常*/
                throw new TreeException(TreeException.TREE_PARENT_IS_CHILDREN,TreeException.TREE_PARENT_IS_CHILDREN_MSG);
            }
        }
        /** 设置级联内部码表示树的结构 */
        treeBaseDo.setCascadeInnerCode(cascadeInnerCode);
    }

    @Override
    public void afterSave(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo treeBaseSaved,IOcpTreeBaseDo treeBaseSubmit) {
        setPTitle(treeBaseDao,treeBaseSaved);
    }

    @Override
    public void beforeSaveAll(IOcpTreeBaseDao treeBaseDao,List<IOcpTreeBaseDo> list) {

    }

    @Override
    public void afterSaveAll(IOcpTreeBaseDao treeBaseDao,List<IOcpTreeBaseDo> listSaved, List<IOcpTreeBaseDo> listSubmit) {

    }

    @Override
    public void beforeUpdateSelective(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo treeBaseDo) {
        /** 更新后父节点如果是自身则抛出异常 */
        if(treeBaseDo.getId().equals(treeBaseDo.getPid())){
            throw new TreeException(TreeException.TREE_PARENT_IS_SELF,TreeException.TREE_PARENT_IS_SELF_MSG);
        }
        IOcpTreeBaseDo treeBaseDoDB = (IOcpTreeBaseDo) treeBaseDao.findById(treeBaseDo.getId()).get();
        String cascadelInnerCode;

        /**根节点为虚拟节点默认设置为root*/
        if(StringUtils.isEmpty(treeBaseDo.getPid())|| OcpTreeBaseConstract.TREE_ROOT_ID.equals(treeBaseDo.getPid())){
            treeBaseDo.setPid(OcpTreeBaseConstract.TREE_ROOT_ID);
            /** 节点移动到根节点下、innerCode 和 cascadeInnerCode相同 */
            cascadelInnerCode=treeBaseDo.getInnerCode();
            treeBaseDo.setCascadeInnerCode(cascadelInnerCode);
            /**更新节点信息操作无需移动子节点*/
        }else if(treeBaseDo.getPid().equals(treeBaseDoDB.getPid())){
            return ;
        }else{
            IOcpTreeBaseDo treeBaseDoParent =  (IOcpTreeBaseDo) treeBaseDao.findById(treeBaseDo.getPid()).get();
            /** 判断父节点不存在 */
            if(treeBaseDoParent==null){
                throw new TreeException(TreeException.TREE_PARENT_NULL,TreeException.TREE_PARENT_NULL_MSG);
            }
            if(treeBaseDoParent.getCascadeInnerCode().startsWith(treeBaseDoDB.getCascadeInnerCode())){
                throw new TreeException(TreeException.TREE_PARENT_IS_CHILDREN,TreeException.TREE_PARENT_IS_CHILDREN_MSG);
            }
            /** 节点移动到新的父节点下、根据新的父节点设置CascadeInnerCode **/
            cascadelInnerCode=treeBaseDoDB.getCascadeInnerCode();
            treeBaseDo.setCascadeInnerCode(treeBaseDoParent.getCascadeInnerCode()+treeBaseDoDB.getInnerCode());
        }
        treeBaseService.moveAllChildren(cascadelInnerCode,treeBaseDo.getCascadeInnerCode());
    }

    @Override
    public void afterUpdateSelective(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo treeBaseDoSaved,IOcpTreeBaseDo treeBaseDoSubmit) {
        setPTitle(treeBaseDao,treeBaseDoSaved);
    }

    @Override
    public void beforeDelete(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo userDoDB) {
        if(treeBaseService.hasChildren(userDoDB.getCascadeInnerCode())){
            throw new TreeException(TreeException.TREE_HAS_CHILDREN_CANNOT_DELETE,TreeException.TREE_HAS_CHILDREN_CANNOT_DELETE_MSG);
        }
    }

    @Override
    public void afterDelete(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo userDoDB) {

    }


    @Override
    public Predicate beforeGetById(IOcpTreeBaseDao treeBaseDao, Root<IOcpTreeBaseDo> root, CriteriaBuilder criteriaBuilder, String s) {
        return null;
    }

    @Override
    public void afterGetById(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo treeBaseDo) {
        setPTitle(treeBaseDao,treeBaseDo);
    }

    @Override
    public Predicate beforePage(IOcpTreeBaseDao treeBaseDao,Root<IOcpTreeBaseDo> root, CriteriaBuilder criteriaBuilder, QueryPageBaseVo queryPageBaseVo) {
        return null;
    }

    @Override
    public void afterPage(IOcpTreeBaseDao treeBaseDao, Page<IOcpTreeBaseDo> page, QueryPageBaseVo queryPageBaseVo) {
    }

    @Override
    public Predicate beforeList(IOcpTreeBaseDao treeBaseDao,Root<IOcpTreeBaseDo> root, CriteriaBuilder criteriaBuilder,QueryPageBaseVo queryBaseVo) {
        return null;
    }

    @Override
    public void afterList(IOcpTreeBaseDao treeBaseDao,List<IOcpTreeBaseDo> list, QueryPageBaseVo queryBaseVo) {
        setPTitle(treeBaseDao,list,queryBaseVo);
    }


    private void setPTitle(IOcpTreeBaseDao treeBaseDao,IOcpTreeBaseDo r){
        if(r==null||StringUtils.isEmpty(r.getPid())){
            return;
        }
        if(OcpTreeBaseConstract.TREE_ROOT_ID.equals(r.getPid())){
            r.setPTitle(OcpTreeBaseConstract.TREE_ROOT_NAME);
            return;
        }
        IOcpTreeBaseDo treeBaseDoParent =  (IOcpTreeBaseDo)treeBaseDao.findById(r.getPid()).get() ;
        if(treeBaseDoParent==null){
            return;
        }
        r.setPTitle(treeBaseDoParent.getTitle());
    }
    private void setPTitle(IOcpTreeBaseDao treeBaseDao,List<IOcpTreeBaseDo> list,QueryPageBaseVo queryBaseVo){
        List<String> pIds=new ArrayList<>();
        for(IOcpTreeBaseDo treeBaseDo:list){
            pIds.add(treeBaseDo.getPid());
        }
        List<IOcpTreeBaseDo> treeBaseDoList=treeBaseDao.findByIdIn(pIds);
        Map<String,IOcpTreeBaseDo> treeBaseDoMap=new HashMap<>();
        for(IOcpTreeBaseDo treeBaseDo:treeBaseDoList){
            treeBaseDoMap.put(treeBaseDo.getId(),treeBaseDo);
        }
        for(IOcpTreeBaseDo treeBaseDo:list){
            String pid=treeBaseDo.getPid();
            if(OcpTreeBaseConstract.TREE_ROOT_ID.equals(pid)){
                treeBaseDo.setPTitle(OcpTreeBaseConstract.TREE_ROOT_NAME);
            }else {
                IOcpTreeBaseDo treeBaseDoParent=treeBaseDoMap.get(pid);
                treeBaseDo.setPTitle(treeBaseDoParent.getTitle());
            }
        }
    }


}
