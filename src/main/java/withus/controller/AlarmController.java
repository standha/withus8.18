package withus.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.catalina.User;
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
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_patient;
import withus.service.AlarmService;
import withus.service.UserService;
import withus.util.Utility;

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
        modelAndView.addObject("previousUrl", "/home");

        return modelAndView;
    }

    @GetMapping("/medicationAlarm")
    @Statistical
    public ModelAndView getMedicationAlarm(){
        ModelAndView modelAndView = new ModelAndView("alarm/medicationAlarm");
        Tbl_patient tbl_patient = getTbl_patient();
     //   Tbl_medication_alarm tbl_medication_alarm = alarmService.getMedicationAlarmByPatient(tbl_patient);

       // modelAndView.addObject("medicationAlarm",tbl_medication_alarm);
        modelAndView.addObject("previousUrl","/alarm");

        return modelAndView;
    }
  /*
    @PostMapping(value = "/medicationAlarm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_medication_alarm> postMedicationAlarm(@RequestBody Tbl_medication_alarm tbl_medication_alarm){
        Tbl_patient tbl_patient = getTbl_patient();
        tbl_medication_alarm.setRegistra
    }*/
}