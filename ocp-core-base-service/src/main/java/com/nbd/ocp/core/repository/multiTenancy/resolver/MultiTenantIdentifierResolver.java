package com.nbd.ocp.core.repository.multiTenancy.resolver;

import com.nbd.ocp.core.repository.multiTenancy.context.TenantContextHolder;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * 这个类是由Hibernate提供的用于识别tenantId的类，当每次执行sql语句被拦截就会调用这个类中的方法来获取tenantId
 * @author lanyuanxiaoyao
 * @version 1.0
 */
public class MultiTenantIdentifierResolver implements CurrentTenantIdentifierResolver{

    // 获取tenantId的逻辑在这个方法里面写
    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContextHolder.getContext().getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
