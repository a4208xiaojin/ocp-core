package com.nbd.ocp.core.repository.multiTenancy.discriminator.entity;

import com.nbd.ocp.core.repository.base.IOcpBaseDo;
import com.nbd.ocp.core.repository.multiTenancy.context.OcpTenantContextHolder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;


/**
 * The type OcpTenant entity.
 */

@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public abstract class AbstractOcpTenancyEntity implements IOcpBaseDo {

  /**
   * Name of the tenant to which the user belongs
   */
  public abstract  String getTenantId();

  public abstract  void setTenantId(String tentnatId);

  @PrePersist
  @PreUpdate
  @PreRemove
  public void onPrePersist() {
    setTenantId(OcpTenantContextHolder.getContext().getTenantId());
  }

}
