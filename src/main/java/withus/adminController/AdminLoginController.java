package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.controller.BaseController;
import withus.dto.Result;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.entity.User;
import withus.service.AlarmService;
import withus.service.UserService;

@Controller
public class AdminLoginController extends AdminBaseController {
    @Autowired
    public AdminLoginController(AuthenticationFacade authenticationFacade, UserService userService) {
        super(userService, authenticationFacade);
    }

    @GetMapping({"/admin_login"})
    public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("Login/admin_login");

        return modelAndView;
    }
}