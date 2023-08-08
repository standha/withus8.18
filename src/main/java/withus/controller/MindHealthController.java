package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.*;
import withus.repository.MindHealthRepository;
import withus.service.CountService;
import withus.service.MindHealthService;
import withus.service.UserService;

import javax.jws.WebParam;
import javax.xml.ws.Action;
import java.time.LocalDate;

@Controller
public class MindHealthController extends BaseController {
    private final MindHealthService mindHealthService;
    private final CountService countService;

    @Autowired
    public MindHealthController(AuthenticationFacade authenticationFacade, UserService userService, MindHealthService mindHealthService, CountService countService) {
        super(userService, authenticationFacade);
        this.mindHealthService = mindHealthService;
        this.countService = countService;
    }

    @GetMapping("/mindHealth")
    public ModelAndView getMindHealth() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/mindHealth");
        modelAndView.addObject("previousURL", "/center");
        User user = getUser();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        modelAndView.addObject("type", user.getType());

        return modelAndView;
    }

    @GetMapping("/minddiary")
    public ModelAndView getMinddiary() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/minddiary");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
        }


        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;

    }

    @GetMapping("/minddiary1")
    public ModelAndView getMindDiary1() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/minddiary1");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("mood", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }
}


