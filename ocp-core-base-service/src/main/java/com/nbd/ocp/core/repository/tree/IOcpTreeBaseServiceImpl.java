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

import com.nbd.ocp.core.repository.base.IOcpBaseServiceImpl;
import com.nbd.ocp.core.repository.crud.IOcpCrudBaseServiceImpl;
import com.nbd.ocp.core.repository.tree.constant.OcpTreeBaseConstract;
import com.nbd.ocp.core.repository.tree.request.OcpTreeQueryBaseVo;
import com.nbd.ocp.core.repository.tree.utils.OcpTreeUtils;
import com.nbd.ocp.core.repository.utils.OcpGenericsUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author jhb
 */
public  interface IOcpTreeBaseServiceImpl<T extends IOcpTreeBaseDo,I extends IOcpTreeBaseDao> extends IOcpTreeBaseService<T,I>, IOcpBaseServiceImpl<T,I> {

    default I getTreeBaseDao(){
        return (I) OcpSpringUtil.getBean(OcpGenericsUtils.getDaoSuperClassGenericsType(getClass(), IOcpTreeBaseDao.class));
    }

    /**
     * 获取所有已生成的innercode中的最大值
     * @return 最大值的64进制
     */
    @Override
    default String getMaxInnerCode(){
        Sort sort= new Sort(Sort.Direction.DESC,"innerCode");
        Pageable pageable = PageRequest.of(0,1,sort);
        Page<T> page= getTreeBaseDao().findAll((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);

        if(page.getTotalElements()>0){
            return page.getContent().get(0).getInnerCode();
        }else{
            return "0";
        }

    }

    /**
     * 移动所有的子节点到新的父节点下
     * @param cascadeInnerCodeParent 父节点修改前的 cascadeInnerCode
     * @param cascadeInnerCodeParentNew 父节点修改后的 cascadeInnerCode
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    default void moveAllChildren(String cascadeInnerCodeParent, String cascadeInnerCodeParentNew){
        if(StringUtils.isEmpty(cascadeInnerCodeParent)||StringUtils.isEmpty(cascadeInnerCodeParentNew)){
            //TODO 抛出空指针异常
        }
        List<T>  treeBaseDos=listAllChildren(cascadeInnerCodeParent);
        if(treeBaseDos!=null&&treeBaseDos.size()>0){
            /** 生成新的级联码 */
            for(T t:treeBaseDos){
                t.setCascadeInnerCode(cascadeInnerCodeParentNew+t.getCascadeInnerCode().substring(cascadeInnerCodeParent.length()));
            }
            /** 更新所有的级联码 */
            getTreeBaseDao().saveAll(treeBaseDos);
        }
    }

    /**
     * 判断是否有子节点
     * @param cascadeInnerCodeParent 父节点id
     * @return
     */
    @Override
    default boolean hasChildren(String cascadeInnerCodeParent){
        List<T>  treeBaseDos=listAllChildren(cascadeInnerCodeParent);
        return treeBaseDos!=null&&treeBaseDos.size()>0;
    }

    default List<T> listAllChildren(String cascadeInnerCodeParent){
        Sort sort= Sort.by("cascadeInnerCode");
        List<T>  treeBaseDos=getTreeBaseDao().findAll((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("cascadeInnerCode"),cascadeInnerCodeParent+"%"));
            predicates.add(criteriaBuilder.notEqual(root.get("cascadeInnerCode"),cascadeInnerCodeParent));
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },sort);
        return treeBaseDos;
    }

    @Override
    default List<T> treeAllChildrenBysCascade(String cascadeInnerCodeParent){
        List<T> list=listAllChildren(cascadeInnerCodeParent);
        return OcpTreeUtils.list2Tree(list);
    }



    @Override
    default  List<T> listTree(OcpTreeQueryBaseVo treeQueryBaseVo){
        String pid=StringUtils.isEmpty(treeQueryBaseVo.getPid())? OcpTreeBaseConstract.TREE_ROOT_ID:treeQueryBaseVo.getPid();
        List<T> list=listAllChildrenById(pid);
        afterListTree(treeQueryBaseVo,list);
        return OcpTreeUtils.list2Tree(list);
    }

    default void afterListTree(OcpTreeQueryBaseVo treeQueryBaseVo, List<T> list){

    }

    default List<T> listAllChildrenById(String id){
        Sort sort= Sort.by("cascadeInnerCode");
        String cascadeInnerCodeParent=null;
        /** id对应的不是根节点则初始化父节点的cascadeInnerCode */
        if(!OcpTreeBaseConstract.TREE_ROOT_ID.equals(id)){
            Optional<T> optionalT=getTreeBaseDao().findOne((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("id"),id));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            });
            if(!optionalT.isPresent()){
                return null;
            }else {

            }
            cascadeInnerCodeParent=optionalT.get().getCascadeInnerCode();
        }
        List<T> result;
        /** 根节点则返回整个树，查询所有的子节点 */
        if(StringUtils.isEmpty(cascadeInnerCodeParent)){
            result= getTreeBaseDao().findAll((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            },sort);
        }else{
            result= listAllChildren(cascadeInnerCodeParent);
        }

        return result;
    }
}
