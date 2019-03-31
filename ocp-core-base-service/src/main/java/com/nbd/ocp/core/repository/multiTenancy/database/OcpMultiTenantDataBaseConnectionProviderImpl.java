package com.nbd.ocp.core.repository.multiTenancy.database;

import com.nbd.ocp.core.repository.multiTenancy.context.OcpTenantContextHolder;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类是Hibernate框架拦截sql语句并在执行sql语句之前更换数据源提供的类
 * @author lanyuanxiaoyao
 * @version 1.0
 */
public class OcpMultiTenantDataBaseConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();
    // 在没有提供tenantId的情况下返回默认数据源
    @Override
    protected DataSource selectAnyDataSource() {
        return getTenantDataSource(OcpTenantContextHolder.getContext().getTenantId());
    }

    // 提供了tenantId的话就根据ID来返回数据源
    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return getTenantDataSource(tenantIdentifier);
    }

    static {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/nbd?useUnicode=yes&characterEncoding=UTF-8&useSSL=false");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("passw0rd");
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");

        DataSourceBuilder dataSourceBuilder1 = DataSourceBuilder.create();
        dataSourceBuilder1.url("jdbc:mysql://127.0.0.1:3306/nbd1?useUnicode=yes&characterEncoding=UTF-8&useSSL=false");
        dataSourceBuilder1.username("root");
        dataSourceBuilder1.password("passw0rd");
        dataSourceBuilder1.driverClassName("com.mysql.jdbc.Driver");

        dataSourceMap.put("nbd1",dataSourceBuilder1.build());
        dataSourceMap.put("nbd",dataSourceBuilder1.build());
        dataSourceMap.put("default", dataSourceBuilder.build());
    }

    // 根据传进来的tenantId决定返回的数据源
    public static DataSource getTenantDataSource(String tenantId) {
        if (dataSourceMap.containsKey(tenantId)) {
            return dataSourceMap.get(tenantId);
        } else {
            return dataSourceMap.get("default");
        }
    }


}
