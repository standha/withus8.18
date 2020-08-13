package com.bluecore.withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bluecore.withus.auth.AuthenticationFacade;
import com.bluecore.withus.entity.User;
import com.bluecore.withus.service.UserService;

@Controller
public class HomeController extends BaseController{
	private final UserService userService;

	@Autowired
	public HomeController(AuthenticationFacade authenticationFacade, UserService userService) {
		super(authenticationFacade);

		this.userService = userService;
	}

	@GetMapping({ "/home" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("home");
		User user = userService.getUserById(getUsername());
		modelAndView.addObject("user", user);

		return modelAndView;
	}
}
