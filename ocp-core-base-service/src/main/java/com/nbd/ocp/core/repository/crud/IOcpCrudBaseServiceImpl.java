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


import com.nbd.ocp.core.repository.constant.OcpCrudBaseDoConstant;
import com.nbd.ocp.core.repository.exception.service.ExistsDataException;
import com.nbd.ocp.core.repository.request.QueryPageBaseVo;
import com.nbd.ocp.core.repository.utils.OcpGenericsUtils;
import com.nbd.ocp.core.repository.utils.OcpSpringUtil;
import com.nbd.ocp.core.utils.bean.OcpBeanCompareUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author jhb
 */
public interface IOcpCrudBaseServiceImpl<T extends IOcpCrudBaseDo,I extends IOcpCrudBaseDao> extends  IOcpCrudBaseService<T,I> {
    default I getCrudBaseDao(){
        return (I) OcpSpringUtil.getBean(OcpGenericsUtils.getDaoSuperClassGenericsType(getClass(), IOcpCrudBaseDao.class));
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    default T save(T t) throws ExistsDataException {
        if(t==null|| StringUtils.isNotEmpty(t.getId())){
            throw new ExistsDataException("数据已存在不允许新增","数据已存在不允许新增");
        }
        T r= (T) getCrudBaseDao().save(t);
        return r;
    }

    @Override
    @javax.transaction.Transactional(rollbackOn = Exception.class)
    default  List<T> saveAll(List<T> list){
        if(list==null|list.size()<1){
            return null;
        }
        List<T> listDB=getCrudBaseDao().saveAll(list);
        return listDB;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    default T updateSelective(T t) {
        T userDoDB=getById(t.getId());
        OcpBeanCompareUtils.combineSydwCore(t,userDoDB);
        T r = (T) getCrudBaseDao().save(userDoDB);
        return r;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    default  void deleteById(String id) {
        T doDB=getById(id);
        getCrudBaseDao().delete(doDB);
    }



    default T getById(String id) {
        Optional<T> optionalT =getCrudBaseDao().findOne((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(OcpCrudBaseDoConstant.DB_COLUMN_ID).as(String.class),id));
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        });
        T r=null;
        if(optionalT.isPresent()){
            r=optionalT.get();
        }
        return r;

    }
    @Override
    default Page<T> page(final QueryPageBaseVo queryPageBaseVo) {
        Page<T> page= getCrudBaseDao().page(queryPageBaseVo);
        return page;
    }
    @Override
    default List<T> list(QueryPageBaseVo queryBaseVo) {
        List<T> list= (List<T>) getCrudBaseDao().list(queryBaseVo);

        return list;
    }
}
