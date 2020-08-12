package com.bluecore.withus.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final UserService userService;

	@Autowired
	public CustomAuthenticationProvider(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		Object credentials = authentication.getCredentials();
		if (!(credentials instanceof String)) {
			throw new AuthenticationException("Non-String credential is not supported.") { };
		}

		String password = (String)credentials;

		User user = userService.getUserByIdPassword(username, password);
		if (user == null) {
			return null;
		} else {
			return new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), new ArrayList<>());
		}
	}
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
