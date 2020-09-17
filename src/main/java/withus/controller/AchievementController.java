package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.service.UserService;

@Controller
public class AchievementController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AchievementController(UserService userService, AuthenticationFacade authenticationFacade) {
        super(userService, authenticationFacade);
    }

    @GetMapping({ "/achievement" })
	@Statistical
	public ModelAndView getGoal() {
		ModelAndView modelAndView = new ModelAndView("achievement/achievement");
		switch (getUser().getType()){
			case PATIENT:
				modelAndView.addObject("level",getUser().getLevel());
				modelAndView.addObject("previousUrl", "/center");
				break;
			case CAREGIVER:
				modelAndView.addObject("level", getCaretaker().getLevel());
				modelAndView.addObject("previousUrl", "/center");
		}
		return modelAndView;
	}
}
