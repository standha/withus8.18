package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.service.GoalService;
import withus.service.UserService;

@Controller
public class InfoController extends BaseController{

    @Autowired
    public InfoController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService) {
        super(userService, authenticationFacade);
    }

    @GetMapping("/info")
    @Statistical
    public ModelAndView getInfo() {
        ModelAndView modelAndView = new ModelAndView("info");
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }
}
