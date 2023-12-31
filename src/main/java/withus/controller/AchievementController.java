package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.*;
import withus.service.CountService;
import withus.entity.User;
import withus.service.SeedService;
import withus.service.UserService;

@Controller
public class AchievementController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final CountService countService;

    private final SeedService seedService;
    public AchievementController(UserService userService, AuthenticationFacade authenticationFacade, CountService countService, SeedService seedService) {
        super(userService, authenticationFacade);
        this.countService = countService;
        this.seedService = seedService;
    }

    @GetMapping({"/achievement"})
    public ModelAndView getGoal() {
        ModelAndView modelAndView = new ModelAndView("achievement/achievement");
        User user = getUser();
        Integer seed = seedService.getSeedSum(user.getUserId(), user.getType());
        modelAndView.addObject("user", user);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("seed", seed);
        switch (user.getType()) {
            case PATIENT:
                Tbl_patient_main_button_count patientCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

                modelAndView.addObject("count", patientCount);
                modelAndView.addObject("level", user.getLevel());
                modelAndView.addObject("previousUrl", "/center");

                logger.info("id:{}, level:{}, type:{}", user.getUserId(), user.getLevel(), user.getType());

                break;

            case CAREGIVER:
                Tbl_caregiver_main_button_count caregiverCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.addObject("count", caregiverCount);
                modelAndView.addObject("level", getCaretaker().getLevel());
                modelAndView.addObject("previousUrl", "/center");

                logger.info("id:{}, type:{}", user.getUserId(), user.getType());

                break;

            default:
                break;
        }

        return modelAndView;
    }
}
