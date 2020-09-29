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

        Tbl_medication_alarm alarm = alarmService.getTodayAlarm(getConnectId());
        if(alarm.getMedicationTimeMorning() == null){
            modelAndView.addObject("morningHour", null);
            modelAndView.addObject("morningMinute", null);
            modelAndView.addObject("morningTime", 0);
        }else{
            modelAndView.addObject("morningHour", alarmService.transformHour(alarm.getMedicationTimeMorning().getHour()));
            modelAndView.addObject("morningMinute", alarmService.transformMinute(alarm.getMedicationTimeMorning().getMinute()));
            modelAndView.addObject("morningTime", alarmService.transformTime(alarm.getMedicationTimeMorning().getHour()));
        }
        if(alarm.getMedicationTimeLunch() == null){
            modelAndView.addObject("lunchHour", null);
            modelAndView.addObject("lunchMinute", null);
            modelAndView.addObject("lunchTime",0);
        }else{
            modelAndView.addObject("lunchHour", alarmService.transformHour(alarm.getMedicationTimeLunch().getHour()));
            modelAndView.addObject("lunchMinute", alarmService.transformMinute(alarm.getMedicationTimeLunch().getMinute()));
            modelAndView.addObject("lunchTime", alarmService.transformTime(alarm.getMedicationTimeLunch().getHour()));
        }
        if(alarm.getMedicationTimeDinner() == null){
            modelAndView.addObject("dinnerHour", null);
            modelAndView.addObject("dinnerMinute", null);
            modelAndView.addObject("dinnerTime", 0);
        }else{
            modelAndView.addObject("dinnerHour", alarmService.transformHour(alarm.getMedicationTimeDinner().getHour()));
            modelAndView.addObject("dinnerMinute", alarmService.transformMinute(alarm.getMedicationTimeDinner().getMinute()));
            modelAndView.addObject("dinnerTime", alarmService.transformTime(alarm.getMedicationTimeDinner().getHour()));
        }

        if(alarmService.getMedicationRecordToday(new RecordKey(getConnectId(),LocalDate.now()))==null){
            modelAndView.addObject("medicationRecord", false);
        }
        else{
            Tbl_medication_record record = alarmService.getMedicationRecordToday(new RecordKey(getConnectId(),LocalDate.now()));
            modelAndView.addObject("medicationRecord", record.isFinished());
        }
        modelAndView.addObject("medicationAlarmOnoff",alarm.isMedicationAlarmOnoff());
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
        Tbl_medication_alarm seved = null;

        try{
            seved = alarmService.upsertMedication(tbl_medication_alarm);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_medication_alarm>builder()
                .code(code)
                .data(seved)
                .build();
    }

    @PostMapping(value = "/pill-history", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_record> getPillHistoryTrue(@RequestBody Tbl_medication_record tbl_medication_record) {
        String userId = getUsername();
        tbl_medication_record.setPk(new RecordKey(userId, LocalDate.now()));
        tbl_medication_record.setWeek(getUser().getWeek());
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
        int hour = 0;
        int minute = 0;
        ModelAndView modelAndView = new ModelAndView("alarm/appointments");
        Tbl_outpatient_visit_alarm appointment = alarmService.getPatientAppointment(getConnectId());
        if(appointment.getOutPatientVisitTime()==null || appointment.getOutPatientVisitDate()==null){
            modelAndView.addObject("hour",null);
            modelAndView.addObject("minute",null);
            modelAndView.addObject("time", 0 );
        }
        else {
            modelAndView.addObject("hour",alarmService.transformHour(appointment.getOutPatientVisitTime().getHour()));
            modelAndView.addObject("minute",alarmService.transformMinute(appointment.getOutPatientVisitTime().getMinute()));
            modelAndView.addObject("time", alarmService.transformTime(appointment.getOutPatientVisitTime().getHour()));
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
                .code(code)
                .data(saved)
                .build();
    }
}
