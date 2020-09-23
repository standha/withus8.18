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
import java.time.LocalTime;
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
        pillHistories  = alarmService.getFinishedRecord(username);
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
        Tbl_medication_alarm seved = null;

        try{
            seved = alarmService.upsertMedication(tbl_medication_alarm);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_medication_alarm>builder()
                .setCode(code)
                .setData(seved)
                .createResult();
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
                .setCode(code)
                .setData(saved)
                .createResult();
    }

    @GetMapping("/appointments")
    @Statistical
    public ModelAndView getAppointments() {
        int hour = 0;
        int minute = 0;
        ModelAndView modelAndView = new ModelAndView("alarm/appointments_");
        Tbl_outpatient_visit_alarm appointment = alarmService.getPatientAppointment(getConnectId());
        if(appointment.getOutPatientVisitTime()==null || appointment.getOutPatientVisitDate()==null){
            modelAndView.addObject("hour",00);
            modelAndView.addObject("minute",00);
            modelAndView.addObject("time", 0 );
        }
        else {
            if (appointment.getOutPatientVisitTime().getHour() > 12) {
                hour = appointment.getOutPatientVisitTime().getHour() - 12;
                minute = appointment.getOutPatientVisitTime().getMinute();
                if(hour >= 10) {
                    modelAndView.addObject("hour", hour);
                }
                else {
                    modelAndView.addObject("hour", "0"+hour);
                }
                if(minute >= 10) {
                    modelAndView.addObject("minute", minute);
                }
                else {
                    modelAndView.addObject("minute", "0"+minute);
                }
                modelAndView.addObject("time", 1);
            }
            else{
                hour = appointment.getOutPatientVisitTime().getHour();
                minute = appointment.getOutPatientVisitTime().getMinute();
                if(hour >= 10) {
                    modelAndView.addObject("hour", hour);
                }
                else {
                    modelAndView.addObject("hour", "0"+hour);
                }
                if(minute >= 10) {
                    modelAndView.addObject("minute", minute);
                }
                else {
                    modelAndView.addObject("minute", "0"+minute);
                }
                modelAndView.addObject("time", 0);
            }
        }
        modelAndView.addObject("appointment",appointment);
        modelAndView.addObject("type",getUser().getType());
        modelAndView.addObject("previousUrl","/alarm");

        return modelAndView;
    }

    @PostMapping("/appointments")
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
                .setCode(code)
                .setData(saved)
                .createResult();
    }

}