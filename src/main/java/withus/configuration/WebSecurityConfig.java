package withus.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import withus.auth.NoOpPasswordEncoder;
import withus.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String REMEMBER_ME_TOKEN = "THIS MUST BE KEPT SECRET AND MAYBE CHANGED";

	private final UserService userService;

	@Autowired
	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf()
				.disable()
			.authorizeRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll()
				.antMatchers("/registerUser", "/saveUser")
					.permitAll()
				.antMatchers("/registerGuardianUser", "/saveGuser")
					.permitAll()
				.anyRequest()
					.authenticated()
					.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login-process")
				.defaultSuccessUrl("/home", true)
				.permitAll()
				.and()
			.rememberMe()
				.rememberMeParameter("remember-me")
				.key(REMEMBER_ME_TOKEN)
				.tokenValiditySeconds(Math.toIntExact(Duration.ofDays(30).getSeconds()))
				.userDetailsService(userService);
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		// TODO: NoOpPasswordEncoder just does nothing for convenience.
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		authenticationProvider.setUserDetailsService(userService);

		return authenticationProvider;
	}
}
