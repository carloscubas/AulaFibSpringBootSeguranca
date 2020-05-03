package br.cubas.usercontrol.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/oauth/check_token", "/oauth/token")
				.permitAll() // this line is necessary to satisfy CORS for web fetch
				.anyRequest().authenticated().and().httpBasic().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { //
	 * HABILITA O FRAME DO H2-CONSOLE http.headers().frameOptions().disable();
	 * 
	 * http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,
	 * "/user/registration").permitAll() .antMatchers(HttpMethod.POST,
	 * "/user/registration").permitAll() .antMatchers(HttpMethod.GET,
	 * "/user/list").hasRole("BASIC") .antMatchers(HttpMethod.GET,
	 * "/user/listadmin").hasRole("ADMIN").and().formLogin()
	 * .loginPage("/user/login").permitAll().and().logout().permitAll(); }
	 */

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
