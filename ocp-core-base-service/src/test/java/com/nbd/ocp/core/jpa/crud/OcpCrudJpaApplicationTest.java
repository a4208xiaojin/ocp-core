package com.nbd.ocp.core.jpa.crud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nbd.ocp.core.jpa.crud.entity.OcpUserDo;
import com.nbd.ocp.core.jpa.crud.service.IOcpUserService;
import com.nbd.ocp.core.repository.OcpRepositoryImpl;
import com.nbd.ocp.core.repository.multiTenancy.context.OcpTenantContextHolder;
import com.nbd.ocp.core.repository.request.QueryPageBaseConstant;
import com.nbd.ocp.core.repository.request.QueryPageBaseVo;
import com.nbd.ocp.core.utils.uri.OcpUriParamUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
@ComponentScan(basePackages="com.nbd.ocp.core.jpa")
@EnableJpaRepositories( basePackages = "com.nbd.ocp.core.jpa",repositoryBaseClass= OcpRepositoryImpl.class)
public class OcpCrudJpaApplicationTest {

	@Autowired
	IOcpUserService userService;
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	public void save() {
		OcpTenantContextHolder.setTenant("nbd1");
		OcpUserDo userDO =new OcpUserDo();
		userDO.setUserName("aaa");
		userDO.setUserCode("bbb");
		userDO.setPassword("ccc");
		userDO.setEmail("d");
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

	public void list() {
		OcpTenantContextHolder.setTenant("nbd1");
		System.out.println(JSON.toJSONString(userService.findAll().size()));
		OcpTenantContextHolder.setTenant("nbd");
		System.out.println(JSON.toJSONString(userService.findAll().size()));
		System.out.println(JSON.toJSONString(userService.listUsers().size()));
	}
	public void testAddController() throws Exception {
		OcpUserDo UserDO =new OcpUserDo();
		UserDO.setEmail("d");
		UserDO.setPassword("dddd");
		UserDO.setSalt("dddddd");
		UserDO.setLocked(false);

		MvcResult result = mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONObject.toJSONString(UserDO)))
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testPageController() throws Exception {
		QueryPageBaseVo queryPageBaseVo =new QueryPageBaseVo();
		queryPageBaseVo.setPageIndex(1);
		queryPageBaseVo.setPageSize(20);

		Map<String,Object> map=new HashMap<>();
		map.put(QueryPageBaseConstant.VO_FIELD_FILTER_METHOD,"findByUserNameEqualsOrUserCodeLikeAndPasswordLikeOrIdIn");
		map.put("userName","aaa");
		map.put("userCode","bbb");
		map.put("password","ccc");
		List<String> ids =new ArrayList<>();
		ids.add("ff80808169d9b5220169d9b5317b0001");
		ids.add("eee");
		map.put("id",ids);
		queryPageBaseVo.setParameters(map);
		queryPageBaseVo.setIds(ids);

		MvcResult result = mockMvc.perform(get("/user/page?"+ OcpUriParamUtil.bean2UrlParamStr( queryPageBaseVo)))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testListController() throws Exception {
		QueryPageBaseVo queryPageBaseVo =new QueryPageBaseVo();

		Map<String,Object> map=new HashMap<>();
		map.put(QueryPageBaseConstant.VO_FIELD_FILTER_METHOD,"findByUserNameEqualsOrUserCodeLikeAndPasswordLikeOrIdIn");
		map.put("userName","aaa");
		map.put("userCode","bbb");
		map.put("password","ccc");
		List<String> ids =new ArrayList<>();
		ids.add("ddd");
		ids.add("eee");
		map.put("id",ids);
		queryPageBaseVo.setParameters(map);
		queryPageBaseVo.setIds(ids);
		MvcResult result = mockMvc.perform(get("/user/list?"+OcpUriParamUtil.bean2UrlParamStr( queryPageBaseVo)))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}

}
