package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
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

    public AchievementController(UserService userService, AuthenticationFacade authenticationFacade) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }

    @GetMapping({"/achievement"})
    @Statistical
    public ModelAndView getGoal() {
        ModelAndView modelAndView = new ModelAndView("achievement/achievement");
        User user = getUser();
        modelAndView.addObject("user", user);
        switch (getUser().getType()) {
            case PATIENT:
                Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.addObject("count", count);
                modelAndView.addObject("level", user.getLevel());
                modelAndView.addObject("previousUrl", "/center");
                logger.info("id:'{}', level:'{}'", user.getUserId(), user.getLevel());
                break;
            case CAREGIVER:
                modelAndView.addObject("level", getCaretaker().getLevel());
                modelAndView.addObject("previousUrl", "/center");
        }
        return modelAndView;
    }
}
