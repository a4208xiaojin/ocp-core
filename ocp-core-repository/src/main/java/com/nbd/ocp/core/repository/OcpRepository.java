package com.nbd.ocp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface OcpRepository<T, ID extends Serializable>
  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

      List<T> getA();

}