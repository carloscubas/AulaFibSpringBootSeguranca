package br.cubas.usercontrol;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.cubas.usercontrol.config.OAuthHelper;
import br.cubas.usercontrol.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UsercontrolApplication.class)
@WebAppConfiguration
public class UsercontrolApplicationTests {

	@Autowired
	private WebApplicationContext webapp;

	@Autowired
	private OAuthHelper authHelper;

	private MockMvc restMvc;

	@Before
	public void setup() {
		restMvc = MockMvcBuilders.webAppContextSetup(webapp).apply(springSecurity()).build();
	}

	// @Test
	public void contextLoads() {
	}

	// @Test
	public void testAnonymous() throws Exception {
		ResultActions resultActions = restMvc.perform(get("/user/list")).andDo(print());
		resultActions.andExpect(status().isUnauthorized());
	}

	// @Test
	public void testAuthenticated() throws Exception {
		RequestPostProcessor bearerToken = authHelper.addBearerToken("test", "ADMIN");
		ResultActions resultActions = restMvc.perform(get("/user/list").with(bearerToken)).andDo(print());
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void testSaveUserAuthenticated() throws Exception {

		User user = new User();
		user.setUsername("cacau");
		user.setPassword("123456");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(user);

		RequestPostProcessor bearerToken = authHelper.addBearerToken("test", "ADMIN");
		ResultActions resultActions = restMvc.perform(
				post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson).with(bearerToken))
				.andDo(print());
		resultActions.andExpect(status().isOk());
	}

}
