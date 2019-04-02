package com.nbd.ocp.core.repository.crud;

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
import com.nbd.ocp.core.repository.constant.OcpCrudBaseDoConstant;
import com.nbd.ocp.core.repository.crud.plugin.IOcpBaseCrudCommonPlugin;
import com.nbd.ocp.core.repository.exception.service.ExistsDataException;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant;
import com.nbd.ocp.core.repository.page.QueryPageBaseVo;
import com.nbd.ocp.core.repository.utils.OcpGenericsUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;
import com.nbd.ocp.core.utils.bean.OcpBeanCompareUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author jhb
 */
@org.springframework.transaction.annotation.Transactional(readOnly = true,rollbackFor = Exception.class)
@OcpCurrentTenant
public interface IOcpCrudBaseService<T extends IOcpCrudBaseDo,I extends IOcpCrudBaseDao> extends IOcpBaseService<T,I> {
    default I getCrudBaseDao(){
        return (I) OcpSpringUtil.getBean(OcpGenericsUtils.getDaoSuperClassGenericsType(getClass(), IOcpCrudBaseDao.class));
    }
    default List<IOcpBaseCrudCommonPlugin> listBaseCrudCommonPlugin(){
        return OcpSpringUtil.getBeansOfTypeList(IOcpBaseCrudCommonPlugin.class);
    }
    @Transactional(rollbackOn = Exception.class)
    default T save(T t) throws ExistsDataException {
        if(t==null|| StringUtils.isNotEmpty(t.getId())){
            throw new ExistsDataException("数据已存在不允许新增","数据已存在不允许新增");
        }
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())){
                    basePlugin.beforeSave(getCrudBaseDao(),t);
                }
            }
        }
        beforeSave(t);
        T r= (T) getCrudBaseDao().save(t);
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterSave(getCrudBaseDao(),r,t);
                }
            }
        }
        afterSave(r,t);
        return r;
    }

    default  void beforeSave(T t){}

    default void afterSave(T tSaved,T tSubmit){}

    @javax.transaction.Transactional(rollbackOn = Exception.class)
    default  List<T> saveAll(List<T> list){
        if(list==null|list.size()<1){
            return null;
        }
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())){
                    basePlugin.beforeSaveAll(getCrudBaseDao(),list);
                }
            }
        }
        beforeSaveAll(list);
        List<T> listDB=getCrudBaseDao().saveAll(list);
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterSaveAll(getCrudBaseDao(),listDB,list);
                }
            }
        }
        afterSaveAll(listDB,list);
        return listDB;
    }
    default  void beforeSaveAll(List<T> list){}

    default void afterSaveAll(List<T> listSaved,List<T> listSubmit){}

    @Transactional(rollbackOn = Exception.class)
    default T updateSelective(T t) {
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.beforeUpdateSelective(getCrudBaseDao(),t);
                }
            }
        }
        T userDoDB=getById(t.getId());
        OcpBeanCompareUtils.combineSydwCore(t,userDoDB);
        beforeUpdate(userDoDB);
        T r = (T) getCrudBaseDao().save(userDoDB);

        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterUpdateSelective(getCrudBaseDao(),r,t);
                }
            }
        }
        afterUpdate(r,t);
        return r;
    }

    default void beforeUpdate(T userDoDB){}

    default void afterUpdate(T tUpdated,T tSubmit){}


    @Transactional(rollbackOn = Exception.class)
    default  void deleteById(String id) {
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        T doDB=getById(id);
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.beforeDelete(getCrudBaseDao(),doDB);
                }
            }
        }
        beforeDeleteById(doDB);
        getCrudBaseDao().delete(doDB);
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterDelete(getCrudBaseDao(),doDB);
                }
            }
        }
        afterDeleteById(doDB);
    }

    default void beforeDeleteById(T doDB){}

    default void afterDeleteById(T doDB){}


    default T getById(String id) {
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        Optional<T> optionalT =getCrudBaseDao().findOne((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(OcpCrudBaseDoConstant.DB_COLUMN_ID).as(String.class),id));
            if(basePlugins!=null&&basePlugins.size()>0){
                for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                    if(basePlugin.support(this.getClass())) {
                        Predicate predicate=basePlugin.beforeGetById(getCrudBaseDao(),root, criteriaBuilder, id);
                        if(predicate!=null){
                            predicates.add(predicate);
                        }
                    }
                }
            }
            Predicate predicate=beforeGetById(root, criteriaBuilder, id);
            if(predicate!=null){
                predicates.add(predicate);
            }

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        });
        T r=null;
        if(optionalT.isPresent()){
            r=optionalT.get();
        }

        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterGetById(getCrudBaseDao(),r);
                }
            }
        }
        afterGetById(r);
        return r;

    }


    default Predicate beforeGetById(Root<T> root, CriteriaBuilder criteriaBuilder, String id){
        return null;
    }
    default void afterGetById(T r){}


    default Page<T> page(final QueryPageBaseVo queryPageBaseVo) {
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        Page<T> page= getCrudBaseDao().page(queryPageBaseVo);
        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterPage(getCrudBaseDao(),page,queryPageBaseVo);
                }
            }
        }
        afterPage(page,queryPageBaseVo);
        return page;
    }
    default Predicate beforePage(List<Predicate> predicates,QueryPageBaseVo queryPageBaseVo,
                                 Root<T> root, CriteriaBuilder criteriaBuilder){
        return null;
    }

    default  void afterPage(Page<T> page,QueryPageBaseVo queryPageBaseVo){

    }
    default List<T> list( QueryPageBaseVo queryBaseVo) {
        List<IOcpBaseCrudCommonPlugin> basePlugins=listBaseCrudCommonPlugin();
        List<T> list= (List<T>) getCrudBaseDao().list(queryBaseVo);

        if(basePlugins!=null&&basePlugins.size()>0){
            for(IOcpBaseCrudCommonPlugin basePlugin:basePlugins){
                if(basePlugin.support(this.getClass())) {
                    basePlugin.afterList(getCrudBaseDao(),list,queryBaseVo);
                }
            }
        }
        afterList(list,queryBaseVo);
        return list;
    }
    default Predicate beforeList(List<Predicate> predicates,QueryPageBaseVo queryBaseVo,Root<T> root, CriteriaBuilder criteriaBuilder){
        return null;
    }
    default void afterList(List<T> list,QueryPageBaseVo queryBaseVo){}
}
