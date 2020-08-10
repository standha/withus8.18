package com.bluecore.withus.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.bluecore.withus.controller")
public class Advice {
	private final Logger logger = LoggerFactory.getLogger(Advice.class);

	@ExceptionHandler(value = Exception.class)
	public void handleException(HttpServletRequest request, Exception exception) {
		logger.error(exception.getLocalizedMessage(), exception);
	}
}
