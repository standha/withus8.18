package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.HelperRequestService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminHomeController extends BaseController{

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public AdminHomeController(AuthenticationFacade authenticationFacade, UserService userService)
    {
        super(userService, authenticationFacade);
    }

    @GetMapping("/user/{userId}")
    public ModelAndView viewPatient(@PathVariable("userId") String userId) {
        // @pathVariable, @ParameterValue, @Header
        User patient = userService.getUserById(userId);
        ModelAndView mav = new ModelAndView();

        mav.addObject("patient", patient);
        mav.setViewName("/Admin/admin_center");

        return mav;
    }

    @PostMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/Login/admin_login?logout";
    }
}
