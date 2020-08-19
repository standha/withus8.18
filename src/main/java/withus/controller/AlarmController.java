package withus.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.alarm.PillHistoryDto;
import withus.entity.User;
import withus.entity.alarm.Appointment;
import withus.entity.alarm.Pill;
import withus.entity.alarm.PillHistory;
import withus.service.AlarmService;
import withus.service.UserService;
import withus.util.Utility;

@Controller
public class AlarmController extends BaseController {
	private final AlarmService alarmService;

	@Autowired
	public AlarmController(AuthenticationFacade authenticationFacade, UserService userService, AlarmService alarmService) {
		super(userService, authenticationFacade);

		this.alarmService = alarmService;
	}

	@GetMapping("/alarm")
	@Statistical
	public ModelAndView getAlarm() {
		ModelAndView modelAndView = new ModelAndView("alarm/alarm");
		modelAndView.addObject("previousUrl", "/home");

		return modelAndView;
	}

	@GetMapping("/pill")
	@Statistical
	public ModelAndView getPill() {
		ModelAndView modelAndView = new ModelAndView("alarm/pill");
		User user = getUser();

		Pill pill = alarmService.getPillByUser(user);

		modelAndView.addObject("pill", pill);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@PostMapping(value = "/pill", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Pill> postPill(@RequestBody Pill pill) {
		User user = getUser();
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

	@GetMapping("/pill-history")
	public ModelAndView getPillHistory(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
		ModelAndView modelAndView = new ModelAndView("alarm/pill-history");

		User user = getUser();

		List<PillHistory> pillHistories;
		if (year == null || month == null) {
			pillHistories = alarmService.getFinishedPillHistoriesByUser(user);
		} else {
			pillHistories = alarmService.getFinishedPillHistoriesByUserYearMonth(user, Year.of(year), Month.of(month));
		}

		modelAndView.addObject("pillHistories", pillHistories);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@PutMapping(value = "/pill-history", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<PillHistory> putPillHistory(@RequestBody PillHistoryDto pillHistoryDto) {
		User user = getUser();

		Result.Code code;
		PillHistory saved = null;

		Long pillId = pillHistoryDto.getPillId();
		if (pillId == null) {
			code = Result.Code.ERROR_MODIFYING_NULL;
		} else {
			Pill existingPill = alarmService.getPillById(pillId);
			if (existingPill == null) {
				code = Result.Code.ERROR_MODIFYING_NULL;
			} else {
				LocalDate today = LocalDate.now();
				Boolean finishedValue = pillHistoryDto.getFinished();
				boolean finished = (finishedValue != null && finishedValue);

				PillHistory pillHistory = alarmService.getPillHistoryByUserDate(user, today);
				if (pillHistory == null) {
					pillHistory = PillHistory.builder()
						.setPill(existingPill)
						.setDate(today)
						.createPillHistory();
				}

				pillHistory.setFinished(finished);

				try {
					saved = alarmService.upsertPillHistory(pillHistory);
					code = Result.Code.OK;
				} catch (Exception exception) {
					logger.error(exception.getLocalizedMessage(), exception);

					code = Result.Code.ERROR_DATABASE;
				}
			}
		}

		return Result.<PillHistory>builder()
			.setCode(code)
			.setData(saved)
			.createResult();
	}

	@GetMapping("/appointments")
	@Statistical
	public ModelAndView getAppointments(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
		ModelAndView modelAndView = new ModelAndView("alarm/appointments");
		User user = getUser();

		List<Appointment> appointments;
		if (year == null || month == null) {
			appointments = alarmService.getAppointments(user);
		} else {
			appointments = alarmService.getAppointments(user, Year.of(year), Month.of(month));
		}

		modelAndView.addObject("appointments", appointments);
		modelAndView.addObject("previousUrl", "/alarm");

		return modelAndView;
	}
	@GetMapping(value = "/appointment/{dateString}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Appointment> getAppointment(@PathVariable String dateString) {
		User user = getUser();

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
		User user = getUser();
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
