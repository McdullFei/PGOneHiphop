package com.atlas.business;

import com.atlas.business.meta.UserBean;
import com.atlas.framework.common.http.HttpClientFactory;
import org.apache.http.client.HttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MockServletContext.class, BusinessApplication.class})
@WebAppConfiguration
public class BusinessApplicationTests {

	/**
	 * 单元测试controller
	 */
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void getHello() throws Exception {
		RequestBuilder request = get("/hello?sKey=d97638447c074ebe9bf133274fe43b97&t=598c2ea0&sign=8C0A3150F8C9C458C8FA14B5CFBB91D0");
		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[]")));

	}


	@Test
	public void login() throws Exception {

		UserBean user = new UserBean();
		user.setUsername("hahahaha");
		user.setPassword("123");

		RequestBuilder request = post("/login?sign=0E30C984601382B16A0DDC8EA65D85B4&t=598c2ea0", user);
		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[]")));

	}

	@Test
	public void getUserById() throws Exception{
		RequestBuilder request = get("/user/2?sign=67E211CD5222A22C869207DFD613E794&t=598c2ea0");
		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[]")));

	}

	@Autowired
	private HttpClientFactory httpClientFactory;

	@Test
	public void httpClientIns() throws Exception {
		Assert.assertNotNull(httpClientFactory.getObject());
	}

	@Test
	public void contextLoads() {
	}

}
