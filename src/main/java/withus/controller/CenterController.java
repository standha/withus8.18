package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
public class CenterController extends BaseController
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public CenterController(AuthenticationFacade authenticationFacade, UserService userService)
	{
		super(userService, authenticationFacade);
	}

	@GetMapping({ "/center" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{

		logger.info("center");
		User user = getUser();
		ModelAndView modelAndView = new ModelAndView();

		if (user.getType().equals(Type.ADMINISTRATOR))
		{
			modelAndView.setViewName("/Admin/admin_Home");
		}
		else
		{
			modelAndView.addObject("type", user.getType());
			System.out.println(user.getType());
			modelAndView.setViewName("home");
		}

		modelAndView.addObject("user", user);

		return modelAndView;
	}
}
