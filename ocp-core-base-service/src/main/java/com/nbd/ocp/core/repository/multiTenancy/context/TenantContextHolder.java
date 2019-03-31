package com.nbd.ocp.core.repository.multiTenancy.context;

import org.springframework.core.NamedThreadLocal;

public abstract class TenantContextHolder {

  private static ThreadLocal<TenantContext> tenants;

  static {
    tenants = new NamedThreadLocal<>("tenants Context");
  }

  public static void reset() {
    tenants.remove();
  }

  public static TenantContext getContext() {
    TenantContext context = tenants.get();
    if (context == null) {
      context = new TenantContext("default");
      setTenant(context);
    }
    return context;
  }

  public static void setTenant(String tenant) {
    TenantContext tenantContext = new TenantContext(tenant);
    setTenant(tenantContext);
  }

  public static void setTenant(TenantContext tenantContext) {
    reset();
    tenants.set(tenantContext);
  }
}
