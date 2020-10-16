package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
import withus.entity.User;
import withus.service.CountService;
import withus.entity.User;
import withus.service.UserService;

@Controller
public class AchievementController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final CountService countService;

    public AchievementController(UserService userService, AuthenticationFacade authenticationFacade, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }

    @GetMapping({"/achievement"})
    public ModelAndView getGoal() {
        ModelAndView modelAndView = new ModelAndView("achievement/achievement");
        User user = getUser();

        modelAndView.addObject("user", user);
        modelAndView.addObject("week",user.getWeek());
        switch (getUser().getType()) {
            case PATIENT:
                Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.addObject("count", count);
                modelAndView.addObject("level", user.getLevel());
                modelAndView.addObject("previousUrl", "/center");

                logger.info("id:{}, level:{}, type:{}", user.getUserId(), user.getLevel(), getUser().getType());

                break;

            case CAREGIVER:
                modelAndView.addObject("level", getCaretaker().getLevel());
                modelAndView.addObject("previousUrl", "/center");

                logger.info("id:{}, type:{}", user.getUserId(), getUser().getType());

                break;

            default:
                break;
        }

        return modelAndView;
    }
}
