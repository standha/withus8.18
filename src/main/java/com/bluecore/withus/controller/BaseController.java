package com.bluecore.withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.bluecore.withus.auth.AuthenticationFacade;
import com.bluecore.withus.entity.User;
import com.bluecore.withus.service.UserService;

public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final UserService userService;
	private final AuthenticationFacade authenticationFacade;

	public BaseController(UserService userService, AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
		this.userService = userService;
	}

	protected Authentication getAuthentication() {
		return authenticationFacade.getAuthentication();
	}
	protected String getUsername() {
		return authenticationFacade.getAuthentication().getName();
	}
	protected User getUser() {
		return userService.getUserById(getUsername());
	}
}
