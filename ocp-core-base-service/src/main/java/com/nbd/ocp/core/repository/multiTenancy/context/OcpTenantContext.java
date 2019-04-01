package com.nbd.ocp.core.repository.multiTenancy.context;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcpTenantContext {

  private String tenantId;

}