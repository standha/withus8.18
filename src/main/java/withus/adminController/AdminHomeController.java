package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.service.UserService;

@Controller
public class AdminHomeController extends AdminBaseController
{
	@Autowired
	public AdminHomeController(AuthenticationFacade authenticationFacade, UserService userService)
	{
		super(userService, authenticationFacade);
	}

	@GetMapping({ "/adminHome" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = new ModelAndView("/Admin/admin_home");
//		User user = getUser();
//		System.out.println("AAAAAAAAAAAAAAAAA : " + "["+ user.getId() +"]");
//		User user = getUser();
//		System.out.println("AAAAAAAAAAAAAAAAA : " + "["+ user.getId() +"]");
//		modelAndView.addObject("user", user);
//		getAllPatient();
		logger.debug("AAAAAAAAAAAAAAA");
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

//		logger.debug("AAAAAAAAAAAAAAA get" + getAllPatient());
		return modelAndView;
	}
}
