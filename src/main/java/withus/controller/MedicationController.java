package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.ProgressKey;
import withus.entity.Tbl_medication_alarm;
import withus.entity.User;
import withus.service.AlarmService;
import withus.service.CountService;
import withus.service.MedicationService;
import withus.service.UserService;

import java.util.List;

@Controller
public class MedicationController extends BaseController {

    private final MedicationService medicationService;
    private final AlarmService alarmService;
    private final CountService countService;

    @Autowired
    public MedicationController (AuthenticationFacade authenticationFacade, UserService userService, MedicationService medicationService, AlarmService alarmService, CountService countService) {
        super(userService, authenticationFacade);
        this.medicationService = medicationService;
        this.alarmService = alarmService;
        this.countService = countService;
    }
    @GetMapping("/medication")
    public ModelAndView getMedication() {
        ModelAndView modelAndView = new ModelAndView("medication/medication");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_medication_alarm alarmOnoff = alarmService.getTodayAlarm(user.getUserId());
//
        modelAndView.addObject("alarmOnoffMorning",alarmOnoff.isAlarmOnoffMorning());
        modelAndView.addObject("alarmOnoffLunch",alarmOnoff.isAlarmOnoffLunch());
        modelAndView.addObject("alarmOnoffDinner",alarmOnoff.isAlarmOnoffDinner());

        modelAndView.addObject("prieviousUrl","/center");

//        tbl_medication_alarm medication = medicationService.getMedicationId(getConnectId())
//        modelAndView.addObject("medication",medication.getMedication());



        modelAndView.addObject("user",user);

//        if (user.getType() == User.Type.PATIENT) {
//            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
//            modelAndView.addObject("count", count);
//        }

        return modelAndView;
    }


}
