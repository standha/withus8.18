package com.bluecore.withus.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView getAlarms() {
		ModelAndView modelAndView = new ModelAndView("alarm/alarm");
		User user = userService.getUserById("pantera");

		modelAndView.addObject("user", user);
		modelAndView.addObject("previousUrl", "/");

		return modelAndView;
	}

	@GetMapping("/pill")
	public ModelAndView getPill() {
		ModelAndView modelAndView = new ModelAndView("alarm/pill");
		User user = userService.getUserById("pantera");

		Pill pill = alarmService.getPill(user);

		modelAndView.addObject("user", user);
		modelAndView.addObject("pill", pill);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@PostMapping(value = "/pill", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String postPill(@RequestBody Pill pill) {
		User user = userService.getUserById("pantera");
		pill.setUser(user);

		Pill saved = alarmService.upsertPill(pill);

		return "TODO: 유의미한 Object로 바꾸기; 어쨌든 성공";
	}

	@GetMapping("/appointments")
	public ModelAndView getAppointments(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
		ModelAndView modelAndView = new ModelAndView("alarm/appointments");
		User user = userService.getUserById("pantera");

		List<Appointment> appointments;
		if (year == null || month == null) {
			appointments = alarmService.getAppointments(user);
		} else {
			appointments = alarmService.getAppointments(user, Year.of(year), Month.of(month));
		}

		modelAndView.addObject("user", user);
		modelAndView.addObject("appointments", appointments);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@GetMapping("/appointment/{date}")
	public Appointment getAppointment(@PathVariable LocalDate date) {
		User user = userService.getUserById("pantera");

		return alarmService.getAppointment(user, date);
	}
	@PostMapping("/appointment")
	@ResponseBody
	public String postAppointment(@RequestBody Appointment appointment) {
		User user = userService.getUserById("pantera");
		appointment.setUser(user);

		Appointment saved = alarmService.upsertAppointment(appointment);

		return "TODO: 유의미한 Object로 바꾸기; 어쨌든 성공";
	}
}
