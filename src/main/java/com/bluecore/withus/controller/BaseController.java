package com.bluecore.withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.bluecore.withus.auth.AuthenticationFacade;

public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final AuthenticationFacade authenticationFacade;

	public BaseController(AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}

	protected Authentication getAuthentication() {
		return authenticationFacade.getAuthentication();
	}
	protected String getUsername() {
		return authenticationFacade.getAuthentication().getName();
	}
}
