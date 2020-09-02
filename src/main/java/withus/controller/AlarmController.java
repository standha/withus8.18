package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.entity.User;
import withus.service.AlarmService;
import withus.service.UserService;

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
        String user = getUsername();
        modelAndView.addObject("previousUrl", "/home");
        System.out.println("alarm Count :  : "+user);
        return modelAndView;
    }

    @GetMapping("/medicationAlarm")
    @Statistical
    public ModelAndView getMedicationAlarm(){
        ModelAndView modelAndView = new ModelAndView("alarm/medicationAlarm");
        User user = getUser();
        String patientContact = getPatientContact();
        String userId = getUsername();
        System.out.println("보호자의 환자의 아이디 : " + patientContact);
        modelAndView.addObject("medicationAlarm",userId);
        modelAndView.addObject("previousUrl","/alarm");

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

    @GetMapping("/appointments")
    @Statistical
    public ModelAndView getAppointments(@RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        ModelAndView modelAndView = new ModelAndView("alarm/appointments");
        User user = getUser();
        String patientContact = getPatientContact();
        String userId = getUsername();
        System.out.println("보호자의 환자의 아이디 : " + patientContact);
        modelAndView.addObject("appointments",userId);
        modelAndView.addObject("previousUrl","/alarm");

        return modelAndView;
    }
    @PostMapping("/appointment")
    @ResponseBody
    public Result<Tbl_outpatient_visit_alarm> PostPatientVisit(@RequestBody Tbl_outpatient_visit_alarm tbl_outpatient_visit_alarm){
        String userId = getUsername();
        tbl_outpatient_visit_alarm.setId(userId);
        Result.Code code;
        Tbl_outpatient_visit_alarm seved = null;

        try{
            seved = alarmService.upsertOutPatientVisit(tbl_outpatient_visit_alarm);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_outpatient_visit_alarm>builder()
                .setCode(code)
                .setData(seved)
                .createResult();
    }
}