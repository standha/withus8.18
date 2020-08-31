package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.Tbl_patient;
import withus.service.UserService;

@Controller
public class HomeController extends BaseController{
	@Autowired
	public HomeController(AuthenticationFacade authenticationFacade, UserService userService) {
		super(userService, authenticationFacade);
	}

	@GetMapping({ "/home" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("home");
		Tbl_patient tbl_patient = getTbl_patient();
		modelAndView.addObject("user", tbl_patient);

		return modelAndView;
	}
}
