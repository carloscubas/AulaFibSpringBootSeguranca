package br.cubas.usercontrol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@EnableAuthorizationServer
@ActiveProfiles("test")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	public static final String CLIENT_ID = "testClient";

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(CLIENT_ID).authorizedGrantTypes("password");
	}
}