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
	public User putMember(@RequestBody User user) {

		return userService.saveUser(user);

	}
/*	@PostMapping({"/saveMember"})
	public ModelAndView putMember(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/login");

		/*String id = request.getParameterMap().get("id");
		String password =request.getParameterMap().get("password").toString();
		String contact = request.getParameterMap().get("contact").toString();

		User user = User.builder()
			.setId(id)
			.setPassword(password)
			.setContact(contact).createUser();

		userService.saveUser(user);*/

		return modelAndView;
	}*/

}
