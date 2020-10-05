package withus.configuration;

import java.io.IOException;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import withus.auth.NoOpPasswordEncoder;
import withus.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private static final String REMEMBER_ME_TOKEN = "THIS MUST BE KEPT SECRET AND MAYBE CHANGED";

	private final UserService userService;

	@Autowired
	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity
				.csrf()
				.disable()
				.authorizeRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll()
				.antMatchers("/registerUser", "/saveUser", "/admin_login", "/login")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/admin_login")
				.loginProcessingUrl("/login-process")
				.defaultSuccessUrl("/center", true)
				.failureUrl("/admin_login.html?error=true")
				.failureHandler(failureHandler())
				.permitAll();

		httpSecurity.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login-process")
				.defaultSuccessUrl("/center", true)
				.failureUrl("/login.html?error=true")
				.permitAll()
				.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.key(REMEMBER_ME_TOKEN)
				.tokenValiditySeconds(Math.toIntExact(Duration.ofDays(30).getSeconds()))
				.userDetailsService(userService);

		httpSecurity
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/admin_login")
							.invalidateHttpSession(true);

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		// TODO: NoOpPasswordEncoder just does nothing for convenience.
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		authenticationProvider.setUserDetailsService(userService);

		return authenticationProvider;
	}
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}

