package withus.controller;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.AlarmService;
import withus.service.CountService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AlarmController extends BaseController {
    private final AlarmService alarmService;
    private final CountService countService;

    @Autowired
    public AlarmController(AuthenticationFacade authenticationFacade, UserService userService, AlarmService alarmService, CountService countService) {
        super(userService, authenticationFacade);
        this.alarmService = alarmService;
        this.countService = countService;
    }

    @GetMapping("/alarm")
    public ModelAndView getAlarm() {
        ModelAndView modelAndView = new ModelAndView("alarm/alarm");
        modelAndView.addObject("previousUrl", "/center");

        User user = getUser();
        modelAndView.addObject("user", user);

        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        return modelAndView;
    }

    @GetMapping("/medicationAlarm")
    public ModelAndView getMedicationAlarm(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("alarm/medicationAlarm");
        User user = getUser();

        Tbl_medication_alarm alarm = alarmService.getTodayAlarm(getConnectId());
        if (alarm.getMedicationTimeMorning() == null) {
            modelAndView.addObject("morningHour", null);
            modelAndView.addObject("morningMinute", null);
            modelAndView.addObject("morningTime", 0);
        } else {
            modelAndView.addObject("morningHour", alarmService.transformHour(alarm.getMedicationTimeMorning().getHour()));
            modelAndView.addObject("morningMinute", alarmService.transformMinute(alarm.getMedicationTimeMorning().getMinute()));
            modelAndView.addObject("morningTime", alarmService.transformTime(alarm.getMedicationTimeMorning().getHour()));
        }
        if (alarm.getMedicationTimeLunch() == null) {
            modelAndView.addObject("lunchHour", null);
            modelAndView.addObject("lunchMinute", null);
            modelAndView.addObject("lunchTime", 0);
        } else {
            modelAndView.addObject("lunchHour", alarmService.transformHour(alarm.getMedicationTimeLunch().getHour()));
            modelAndView.addObject("lunchMinute", alarmService.transformMinute(alarm.getMedicationTimeLunch().getMinute()));
            modelAndView.addObject("lunchTime", alarmService.transformTime(alarm.getMedicationTimeLunch().getHour()));
        }
        if (alarm.getMedicationTimeDinner() == null) {
            modelAndView.addObject("dinnerHour", null);
            modelAndView.addObject("dinnerMinute", null);
            modelAndView.addObject("dinnerTime", 0);
        } else {
            modelAndView.addObject("dinnerHour", alarmService.transformHour(alarm.getMedicationTimeDinner().getHour()));
            modelAndView.addObject("dinnerMinute", alarmService.transformMinute(alarm.getMedicationTimeDinner().getMinute()));
            modelAndView.addObject("dinnerTime", alarmService.transformTime(alarm.getMedicationTimeDinner().getHour()));
        }

        if (alarmService.getMedicationRecordToday(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("medicationRecord", false);
        } else {
            Tbl_medication_record record = alarmService.getMedicationRecordToday(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("medicationRecord", record.isFinished());
        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("medicationAlarmOnoff", alarm.isMedicationAlarmOnoff());
        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/alarm");

        logger.info("id:{}, url:{} , alarmOnOff:{}, morning:{}, lunch:{}, dinner:{}", user.getUserId(), request.getRequestURL(), alarm.isMedicationAlarmOnoff(), alarm.getMedicationTimeMorning(), alarm.getMedicationTimeLunch(), alarm.getMedicationTimeDinner());

        return modelAndView;
    }

    @GetMapping("/pill-history")
    public ModelAndView getPillHistory(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        ModelAndView modelAndView = new ModelAndView("alarm/pill-history");
        List<Tbl_medication_record> pillHistories;
        pillHistories = alarmService.getFinishedRecord(getConnectId());

        User user = getUser();
        modelAndView.addObject("user", user);

        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("pillHistories", pillHistories);
        modelAndView.addObject("previousUrl", "/alarm");

        logger.info("id:{}", user.getUserId());

        return modelAndView;
    }

    @PostMapping(value = "/medicationAlarm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_alarm> postMedicationAlarm(@RequestBody Tbl_medication_alarm tbl_medication_alarm) {
        User user = getUser();
        tbl_medication_alarm.setId(user.getUserId());
        Result.Code code;
        Tbl_medication_alarm saved = null;

        logger.info("id:{}", user.getUserId());

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = alarmService.upsertMedication(tbl_medication_alarm);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
            logger.info("id:{}, dinner:{}, lunch:{}, morning:{}, code:{}", user.getUserId(), saved.getMedicationTimeDinner(), saved.getMedicationTimeLunch(), saved.getMedicationTimeMorning(), code);

        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);

            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_medication_alarm>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @PostMapping(value = "/pill-history", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_record> getPillHistoryTrue(@RequestBody Tbl_medication_record tbl_medication_record) {
        User user = getUser();
        tbl_medication_record.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_medication_record.setWeek(user.getWeek());

        logger.info("id:{}, week:{}", user.getUserId(), user.getWeek());

        Result.Code code;
        Tbl_medication_record saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = alarmService.upsertTrueRecord(tbl_medication_record);
                logger.info("id:{}, finished:{}", user.getUserId(), saved.isFinished());
                code = Result.Code.OK;
            } else if (user.getWeek() == 25) {
                throw new IllegalStateException("25 Weeks User try input data [warn]");
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);

            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_medication_record>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @GetMapping("/appointments")
    public ModelAndView getAppointments(HttpServletRequest request, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        int hour = 0;
        int minute = 0;
        ModelAndView modelAndView = new ModelAndView("alarm/appointments");
        User user = getUser();
        Tbl_outpatient_visit_alarm appointment = alarmService.getPatientAppointment(getConnectId());

        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        if (appointment.getOutPatientVisitTime() == null || appointment.getOutPatientVisitDate() == null) {
            modelAndView.addObject("hour", null);
            modelAndView.addObject("minute", null);
            modelAndView.addObject("time", 0);
        } else {
            modelAndView.addObject("hour", alarmService.transformHour(appointment.getOutPatientVisitTime().getHour()));
            modelAndView.addObject("minute", alarmService.transformMinute(appointment.getOutPatientVisitTime().getMinute()));
            modelAndView.addObject("time", alarmService.transformTime(appointment.getOutPatientVisitTime().getHour()));
        }

        modelAndView.addObject("appointment", appointment);
        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/alarm");

        logger.info("id:{}, url:{}, appointDate:{}, appointTime:{}", user.getUserId(), request.getRequestURL(), appointment.getOutPatientVisitDate(), appointment.getOutPatientVisitTime());

        return modelAndView;
    }

    @PostMapping("/appointments")
    @ResponseBody
    public Result<Tbl_outpatient_visit_alarm> PostPatientVisit(@RequestBody Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm) {
        User user = getUser();
        tbl_outpatient_visit_alarm.setId(user.getUserId());
        Result.Code code;
        Tbl_outpatient_visit_alarm saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = alarmService.upsertOutPatientVisit(tbl_outpatient_visit_alarm);
                logger.info("id:{}, date:{}, time:{}", user.getUserId(), saved.getOutPatientVisitTime(), saved.getOutPatientVisitDate());
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_outpatient_visit_alarm>builder()
                .code(code)
                .data(saved)
                .build();
    }
}