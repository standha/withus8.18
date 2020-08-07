package com.bluecore.withus.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;
import com.bluecore.withus.entity.alarms.Pill;
import com.bluecore.withus.service.AlarmService;
import com.bluecore.withus.service.UserService;

@Controller
public class AlarmController {
	private final UserService userService;
	private final AlarmService alarmService;

	@Autowired
	public AlarmController(UserService userService, AlarmService alarmService) {
		this.userService = userService;
		this.alarmService = alarmService;
	}

	@GetMapping("/alarm")
	public ModelAndView getAlarms(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("alarm/alarm");
		User user = userService.getUserById("pantera");

		modelAndView.addObject("user", user);
		modelAndView.addObject("previousUrl", "/");

		return modelAndView;
	}

	@GetMapping("/pill")
	public ModelAndView getPill(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("alarm/pill");
		User user = userService.getUserById("pantera");

		Pill pill = alarmService.getPill(user);

		modelAndView.addObject("user", user);
		modelAndView.addObject("pill", pill);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@PostMapping(value = "/pill", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String postPill(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.getUserById("pantera");

		return "성공?";
	}

	@GetMapping("/appointment")
	public ModelAndView getAppointment(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("alarm/appointment");
		User user = userService.getUserById("pantera");

		List<Appointment> appointments = alarmService.getAppointments(user);

		modelAndView.addObject("user", user);
		modelAndView.addObject("appointments", appointments);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
}
