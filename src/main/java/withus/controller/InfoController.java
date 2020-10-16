package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
import withus.entity.User;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InfoController extends BaseController {
    private final CountService countService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public InfoController(AuthenticationFacade authenticationFacade, UserService userService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }

    @GetMapping("/info")
    public ModelAndView getInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("info");
        User user = getUser();

        logger.info("id:{}, url:{}, type:{}, level:{}, week:{}", user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek());

        modelAndView.addObject("user", user);
        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("previousUrl", "/center");
        modelAndView.addObject("week",user.getWeek());
        return modelAndView;
    }
}
