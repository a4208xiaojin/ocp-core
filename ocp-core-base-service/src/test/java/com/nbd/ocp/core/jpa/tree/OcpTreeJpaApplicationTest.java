package com.nbd.ocp.core.jpa.tree;

import com.alibaba.fastjson.JSON;
import com.nbd.ocp.core.jpa.tree.service.IOcpMenuService;
import com.nbd.ocp.core.repository.OcpRepositoryImpl;
import com.nbd.ocp.core.repository.tree.request.OcpTreeQueryBaseVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
@ComponentScan(basePackages="com.nbd.ocp.core.jpa")
@EnableJpaRepositories( basePackages = "com.nbd.ocp.core.jpa",repositoryBaseClass= OcpRepositoryImpl.class)
public class OcpTreeJpaApplicationTest {

	@Autowired
	IOcpMenuService menuService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testListTree(){
		OcpTreeQueryBaseVo treeQueryBaseVo=new OcpTreeQueryBaseVo();
		treeQueryBaseVo.setPid("root");
		System.out.println(JSON.toJSONString(menuService.listTree(treeQueryBaseVo)));
	}

}
