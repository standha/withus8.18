package com.bluecore.withus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.bluecore.withus.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private final UserService userService;

	@Autowired
	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()
		.antMatchers( "/", "LogIn/**", "/css/**", "/js/**", "/images/**","/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/home")
			.and()
		.logout()
			.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		/*UserDetails user =
			User.withDefaultPasswordEncoder()
				.username("user")
				.password(user.getPassword())
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);*/
		return userService;
	}
}
