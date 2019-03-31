package com.nbd.ocp.core.jpa;

import com.alibaba.fastjson.JSON;
import com.nbd.ocp.core.jpa.dao.OcpUserDo;
import com.nbd.ocp.core.jpa.service.IOcpUserService;
import com.nbd.ocp.core.repository.OcpRepositoryImpl;
import com.nbd.ocp.core.repository.multiTenancy.context.OcpTenantContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
@ComponentScan(basePackages="com.nbd.ocp.core.jpa")
@EnableJpaRepositories( basePackages = "com.nbd.ocp.core.jpa",repositoryBaseClass= OcpRepositoryImpl.class)
public class OcpJpaApplicationTest {

	@Autowired
	IOcpUserService userService;

	@Test
	@Order(3)
	public void save() {
		OcpTenantContextHolder.setTenant("nbd1");
		OcpUserDo userDO =new OcpUserDo();
		userDO.setEmail("d");
		userDO.setPassword("ddd");
		userDO.setSalt("ddddd");
		userDO.setLocked(false);
		OcpUserDo userVO =userService.save(userDO);
		System.out.println(userVO);
		OcpTenantContextHolder.setTenant("nbd");
		OcpUserDo userDO1 =new OcpUserDo();
		userDO1.setEmail("d");
		userDO1.setPassword("ddd");
		userDO1.setSalt("ddddd");
		userDO1.setLocked(false);
		OcpUserDo userVO1 =userService.save(userDO1);
		System.out.println(userVO1);

	}

	@Test
	@Order(0)
	public void list() {
		OcpTenantContextHolder.setTenant("nbd1");
		System.out.println(JSON.toJSONString(userService.findAll().size()));
		OcpTenantContextHolder.setTenant("nbd");
		System.out.println(JSON.toJSONString(userService.findAll().size()));
		System.out.println(JSON.toJSONString(userService.listUsers().size()));


	}

}
