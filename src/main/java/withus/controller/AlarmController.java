package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.MedicationAlarmService;
import withus.service.CountService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AlarmController extends BaseController {
    private final MedicationAlarmService medicationAlarmService;
    private final CountService countService;

    @Autowired
    public AlarmController(AuthenticationFacade authenticationFacade, UserService userService, MedicationAlarmService medicationAlarmService, CountService countService) {
        super(userService, authenticationFacade);
        this.medicationAlarmService = medicationAlarmService;
        this.countService = countService;
    }

    @GetMapping("/alarm")
    public ModelAndView getAlarm() {
        ModelAndView modelAndView = new ModelAndView("alarm/alarm");
        modelAndView.addObject("previousUrl", "/center");

        User user = getUser();
        modelAndView.addObject("user", user);

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        return modelAndView;
    }

    @GetMapping("/medicationAlarm")
    public ModelAndView getMedicationAlarm() {
        ModelAndView modelAndView = new ModelAndView("alarm/medicationAlarm");
        User user = getUser();

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        Tbl_medication_alarm alarm = medicationAlarmService.getMedication(new RecordKey(user.getUserId(),LocalDate.now()));
        if (alarm.getMedicationTimeMorning() == null) {
            modelAndView.addObject("morningHour", null);
            modelAndView.addObject("morningMinute", null);
            modelAndView.addObject("morningTime", 0);
        } else {
            modelAndView.addObject("morningHour", medicationAlarmService.transformHour(alarm.getMedicationTimeMorning().getHour()));
            modelAndView.addObject("morningMinute", medicationAlarmService.transformMinute(alarm.getMedicationTimeMorning().getMinute()));
            modelAndView.addObject("morningTime", medicationAlarmService.transformTime(alarm.getMedicationTimeMorning().getHour()));
        }
        if (alarm.getMedicationTimeLunch() == null) {
            modelAndView.addObject("lunchHour", null);
            modelAndView.addObject("lunchMinute", null);
            modelAndView.addObject("lunchTime", 0);
        } else {
            modelAndView.addObject("lunchHour", medicationAlarmService.transformHour(alarm.getMedicationTimeLunch().getHour()));
            modelAndView.addObject("lunchMinute", medicationAlarmService.transformMinute(alarm.getMedicationTimeLunch().getMinute()));
            modelAndView.addObject("lunchTime", medicationAlarmService.transformTime(alarm.getMedicationTimeLunch().getHour()));
        }
        if (alarm.getMedicationTimeDinner() == null) {
            modelAndView.addObject("dinnerHour", null);
            modelAndView.addObject("dinnerMinute", null);
            modelAndView.addObject("dinnerTime", 0);
        } else {
            modelAndView.addObject("dinnerHour", medicationAlarmService.transformHour(alarm.getMedicationTimeDinner().getHour()));
            modelAndView.addObject("dinnerMinute", medicationAlarmService.transformMinute(alarm.getMedicationTimeDinner().getMinute()));
            modelAndView.addObject("dinnerTime", medicationAlarmService.transformTime(alarm.getMedicationTimeDinner().getHour()));
        }

        if (medicationAlarmService.getMedication(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("medicationRecordMorning", null);
            modelAndView.addObject("medicationRecordLunch", null);
            modelAndView.addObject("medicationRecordDinner", null);
        } else {
            modelAndView.addObject("medicationRecordMorning", alarm.getMorning());
            modelAndView.addObject("medicationRecordLunch", alarm.getLunch());
            modelAndView.addObject("medicationRecordDinner", alarm.getDinner());
        }

        if (medicationAlarmService.getMedication(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("alarmOnoffMorning", null);
            modelAndView.addObject("alarmOnoffLunch", null);
            modelAndView.addObject("alarmOnoffDinner", null);
        }else{
            modelAndView.addObject("alarmOnoffMorning", alarm.isAlarmOnoffMorning());
            modelAndView.addObject("alarmOnoffLunch", alarm.isAlarmOnoffLunch());
            modelAndView.addObject("alarmOnoffDinner", alarm.isAlarmOnoffDinner());
       }
        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/alarm");

        return modelAndView;
    }

    @GetMapping("/medication")
    public ModelAndView getMedication(HttpServletRequest request) {
        logger.info("getmedication success ");
        ModelAndView modelAndView = new ModelAndView("medication/medication");
        User user = getUser();


        Tbl_medication_alarm intakemedi = medicationAlarmService.getMedication(new RecordKey(user.getUserId(), LocalDate.now()));


        modelAndView.addObject("alarmOnoffMorning",intakemedi.isAlarmOnoffMorning());
        modelAndView.addObject("alarmOnoffLunch", intakemedi.isAlarmOnoffLunch());
        modelAndView.addObject("alarmOnoffDinner", intakemedi.isAlarmOnoffDinner());

        if (intakemedi.getMorning() == null || intakemedi.getLunch() == null || intakemedi.getDinner() == null) {

            modelAndView.addObject("morning", "");
            modelAndView.addObject("lunch", "");
            modelAndView.addObject("dinner", "");
        } else {
            modelAndView.addObject("morning", intakemedi.getMorning());
            modelAndView.addObject("lunch", intakemedi.getLunch());
            modelAndView.addObject("dinner", intakemedi.getDinner());
        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }


        modelAndView.addObject("user", user);
        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("prieviousUrl", "/center");

        return modelAndView;
    }



    @GetMapping("/pill-history")
    public ModelAndView getPillHistory(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        ModelAndView modelAndView = new ModelAndView("alarm/pill-history");

        User user = getUser();

        List<Tbl_medication_alarm> alarm = medicationAlarmService.getALlMedicationRecord(user.getUserId());


        modelAndView.addObject("user", user);


        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("pillHistories", alarm);
        modelAndView.addObject("previousUrl", "/alarm");

        logger.info("id:{}", user.getUserId());

        return modelAndView;
    }

    @PostMapping(value = "/medicationAlarm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_alarm> postMedicationAlarm(@RequestBody Tbl_medication_alarm tbl_medication_alarm) {
        User user = getUser();
        tbl_medication_alarm.setPk(new RecordKey(user.getUsername(),LocalDate.now()));
        tbl_medication_alarm.setWeek(user.getWeek());
        Result.Code code;
        Tbl_medication_alarm saved = null;

        logger.info("id:{}", user.getUserId());

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = medicationAlarmService.upsertMedication(tbl_medication_alarm);
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
    public Result<Tbl_medication_alarm> getPillHistoryTrue(@RequestBody Tbl_medication_alarm tbl_medication_alarm) {
        User user = getUser();

        logger.info("id:{}, week:{}", user.getUserId(), user.getWeek());

        Result.Code code;
        Tbl_medication_alarm saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = medicationAlarmService.upsertMedication(tbl_medication_alarm);
//                logger.info("id:{}, finished:{}", user.getUserId(), saved.isFinished());
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

        return Result.<Tbl_medication_alarm>builder()
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
        Tbl_outpatient_visit_alarm appointment = medicationAlarmService.getPatientAppointment(getConnectId());

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        if (appointment.getOutPatientVisitTime() == null || appointment.getOutPatientVisitDate() == null) {
            modelAndView.addObject("hour", null);
            modelAndView.addObject("minute", null);
            modelAndView.addObject("time", 0);
        } else {
            modelAndView.addObject("hour", medicationAlarmService.transformHour(appointment.getOutPatientVisitTime().getHour()));
            modelAndView.addObject("minute", medicationAlarmService.transformMinute(appointment.getOutPatientVisitTime().getMinute()));
            modelAndView.addObject("time", medicationAlarmService.transformTime(appointment.getOutPatientVisitTime().getHour()));
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
                saved = medicationAlarmService.upsertOutPatientVisit(tbl_outpatient_visit_alarm);
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


    @PostMapping(value = "/medication", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_alarm> postMedication(@RequestBody Tbl_medication_alarm tbl_medication_alarm) {
        User user = getUser();
        tbl_medication_alarm.setPk(new RecordKey(user.getUsername(), LocalDate.now()));
        tbl_medication_alarm.setWeek(user.getWeek());


        tbl_medication_alarm.getPk();
        Result.Code code;
        Tbl_medication_alarm saved = null;

        logger.info("medication post mapping");

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = medicationAlarmService.upsertMedication(tbl_medication_alarm);
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
        return Result.<Tbl_medication_alarm>builder()
                .code(code)
                .data(saved)
                .build();
    }


}
