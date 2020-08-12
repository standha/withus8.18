package com.bluecore.withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.service.UserService;

@Controller
public class LoginController {
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping({ "/", "/login" })
	public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/login");

		return modelAndView;
	}

	@GetMapping({"/registerMember"})
	public ModelAndView getRegisterPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/register");
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.addObject("previousUrl", "LogIn/login");

		return modelAndView;
	}

	@PostMapping(value = "/saveMember", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ModelAndView putMember(@RequestBody User user) {
		ModelAndView modelAndView = new ModelAndView("/home");
		modelAndView.addObject("previousUrl", "LogIn/login");
		userService.saveUser(user);

		return modelAndView;
	}

}
