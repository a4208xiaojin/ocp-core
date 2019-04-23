package com.nbd.ocp.core.repository.context;

import org.springframework.core.NamedThreadLocal;

public abstract class OcpTenantContextHolder {

  private static ThreadLocal<OcpTenantContext> tenants;

  static {
    tenants = new NamedThreadLocal<>("tenants Context");
  }

  public static void reset() {
    tenants.remove();
  }

  public static OcpTenantContext getContext() {
    OcpTenantContext context = tenants.get();
    if (context == null) {
      context = new OcpTenantContext("default");
      setTenant(context);
    }
    return context;
  }

  public static void setTenant(String tenant) {
    OcpTenantContext ocpTenantContext = new OcpTenantContext(tenant);
    setTenant(ocpTenantContext);
  }

  public static void setTenant(OcpTenantContext ocpTenantContext) {
    reset();
    tenants.set(ocpTenantContext);
  }

}
