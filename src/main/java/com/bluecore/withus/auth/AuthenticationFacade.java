package com.bluecore.withus.auth;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	Authentication getAuthentication();
}
