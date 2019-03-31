package com.nbd.ocp.core.repository;

import com.nbd.ocp.core.repository.page.QueryPageBaseVo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface OcpRepository<T, ID extends Serializable>
  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

      Page<T> page(final QueryPageBaseVo queryPageBaseVo);

      List<T> list(QueryPageBaseVo queryBaseVo);

}