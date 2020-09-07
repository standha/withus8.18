package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
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
		User user = getUser();
		modelAndView.addObject("user", user);

		// TODO: to be removed
		System.out.printf("caretaker: %s\n", getCaretaker());

		return modelAndView;
	}
}
