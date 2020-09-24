package withus.controller;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.AlarmService;
import withus.service.UserService;
import java.time.LocalDate;
import java.util.List;


@Controller
public class AlarmController extends BaseController{
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AuthenticationFacade authenticationFacade, UserService userService, AlarmService alarmService){
        super(userService, authenticationFacade);

        this.alarmService = alarmService;

    }

    @GetMapping("/alarm")
    @Statistical
    public ModelAndView getAlarm() {
        ModelAndView modelAndView = new ModelAndView("alarm/alarm");
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }

    @GetMapping("/medicationAlarm")
    @Statistical
    public ModelAndView getMedicationAlarm(){
        ModelAndView modelAndView = new ModelAndView("alarm/medicationAlarm");
        if(alarmService.getTodayAlarm(getConnectId())== null){
            modelAndView.addObject("medicationTimeMorning","");
            modelAndView.addObject("medicationTimeLunch","");
            modelAndView.addObject("medicationTimeDinner","");
            modelAndView.addObject("medicationAlarmOnoff","");
        }else{
            Tbl_medication_alarm alarm = alarmService.getTodayAlarm(getConnectId());
            modelAndView.addObject("medicationTimeMorning",alarm.getMedicationTimeMorning());
            modelAndView.addObject("medicationTimeLunch",alarm.getMedicationTimeLunch());
            modelAndView.addObject("medicationTimeDinner",alarm.getMedicationTimeDinner());
            modelAndView.addObject("medicationAlarmOnoff",alarm.isMedicationAlarmOnoff());
        }
        modelAndView.addObject("type",getUser().getType());
        modelAndView.addObject("previousUrl","/alarm");

        return modelAndView;
    }
    @GetMapping("/pill-history")
    public ModelAndView getPillHistory(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        ModelAndView modelAndView = new ModelAndView("alarm/pill-history");
        String username = getUsername();
        List<Tbl_medication_record> pillHistories;
        pillHistories  = alarmService.getFinishedRecord(getConnectId());
        modelAndView.addObject("pillHistories", pillHistories);
        modelAndView.addObject("previousUrl", "/alarm");

        return modelAndView;
    }
    @PostMapping(value = "/medicationAlarm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_alarm> postMedicationAlarm(@RequestBody Tbl_medication_alarm tbl_medication_alarm){
        String userId = getUsername();
        tbl_medication_alarm.setId(userId);

        Result.Code code;
        Tbl_medication_alarm saved = null;

        try{
            saved = alarmService.upsertMedication(tbl_medication_alarm);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
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
        String userId = getUsername();
        tbl_medication_record.setPk(new RecordKey(userId, LocalDate.now()));
        Result.Code code;
        Tbl_medication_record saved = null;
        try {
            saved = alarmService.upsertTrueRecord(tbl_medication_record);
            code = Result.Code.OK;
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
    @Statistical
    public ModelAndView getAppointments(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        ModelAndView modelAndView = new ModelAndView("alarm/appointments");
        Tbl_outpatient_visit_alarm appointment = alarmService.getPatientAppointment(getConnectId());
        modelAndView.addObject("type",getUser().getType());
        modelAndView.addObject("appointments",appointment);
        modelAndView.addObject("previousUrl","/alarm");

        return modelAndView;
    }
    @PostMapping("/appointment")
    @ResponseBody
    public Result<Tbl_outpatient_visit_alarm> PostPatientVisit(@RequestBody Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm){
        String userId = getUsername();
        tbl_outpatient_visit_alarm.setId(userId);
        Result.Code code;
        Tbl_outpatient_visit_alarm saved = null;

        try{
            saved = alarmService.upsertOutPatientVisit(tbl_outpatient_visit_alarm);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_outpatient_visit_alarm>builder()
                .code(code)
                .data(saved)
                .build();
    }
}
