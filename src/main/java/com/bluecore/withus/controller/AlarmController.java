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

import com.bluecore.withus.auth.AuthenticationFacade;
import com.bluecore.withus.dto.Result;
import com.bluecore.withus.entity.User;
import com.bluecore.withus.entity.alarms.Appointment;
import com.bluecore.withus.entity.alarms.Pill;
import com.bluecore.withus.service.AlarmService;
import com.bluecore.withus.service.UserService;
import com.bluecore.withus.util.Utility;

@Controller
public class AlarmController extends BaseController {
	private final UserService userService;
	private final AlarmService alarmService;

	@Autowired
	public AlarmController(AuthenticationFacade authenticationFacade, UserService userService, AlarmService alarmService) {
		super(authenticationFacade);

		this.userService = userService;
		this.alarmService = alarmService;
	}

	@GetMapping("/alarm")
	public ModelAndView getAlarms() {
		ModelAndView modelAndView = new ModelAndView("alarm/alarm");
		User user = userService.getUserById(getUsername());

		modelAndView.addObject("user", user);
		modelAndView.addObject("previousUrl", "/home");

		return modelAndView;
	}

	@GetMapping("/pill")
	public ModelAndView getPill() {
		ModelAndView modelAndView = new ModelAndView("alarm/pill");
		User user = userService.getUserById(getUsername());

		Pill pill = alarmService.getPill(user);

		modelAndView.addObject("user", user);
		modelAndView.addObject("pill", pill);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@PostMapping(value = "/pill", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Pill> postPill(@RequestBody Pill pill) {
		User user = userService.getUserById(getUsername());
		pill.setUser(user);

		Result.Code code;
		Pill saved = null;
		try {
			saved = alarmService.upsertPill(pill);
			code = Result.Code.OK;
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage(), exception);

			code = Result.Code.ERROR_DATABASE;
		}

		return Result.<Pill>builder()
			.setCode(code)
			.setData(saved)
			.createResult();
	}

	@GetMapping("/appointments")
	public ModelAndView getAppointments(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
		ModelAndView modelAndView = new ModelAndView("alarm/appointments");
		User user = userService.getUserById(getUsername());

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
	@GetMapping(value = "/appointment/{dateString}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Appointment> getAppointment(@PathVariable String dateString) {
		User user = userService.getUserById(getUsername());

		LocalDate date = Utility.parseDate(dateString);

		Result.Code code;
		Appointment appointment = null;
		try {
			appointment = alarmService.getAppointment(user, date);
			if (appointment == null) {
				code = Result.Code.OK_NULL;
			} else {
				code = Result.Code.OK;
			}
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage(), exception);

			code = Result.Code.ERROR_DATABASE;
		}

		return Result.<Appointment>builder()
			.setCode(code)
			.setData(appointment)
			.createResult();
	}
	@PostMapping("/appointment")
	@ResponseBody
	public Result<Appointment> postAppointment(@RequestBody Appointment appointment) {
		User user = userService.getUserById(getUsername());
		appointment.setUser(user);

		Result.Code code;
		Appointment saved = null;
		try {
			saved = alarmService.upsertAppointment(appointment);
			code = Result.Code.OK;
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage(), exception);

			code = Result.Code.ERROR_DATABASE;
		}

		return Result.<Appointment>builder()
			.setCode(code)
			.setData(saved)
			.createResult();
	}
}
