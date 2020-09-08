package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.entity.User.Type;
import withus.service.UserService;

@Controller
public class LogoutController extends BaseController
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public LogoutController(AuthenticationFacade authenticationFacade, UserService userService)
	{
		super(userService, authenticationFacade);
	}

	@GetMapping({ "/logout" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = new ModelAndView("/logout");

		return modelAndView;
	}
}
