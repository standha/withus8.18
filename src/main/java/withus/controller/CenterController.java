package withus.controller;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.User;
import withus.entity.User.Type;
import withus.entity.WithusHelpRequest;
import withus.service.CenterService;
import withus.service.UserService;

@Controller
public class CenterController extends BaseController
{
	private final CenterService centerService;

	@Autowired
	public CenterController(AuthenticationFacade authenticationFacade, UserService userService, CenterService centerService)
	{
		super(userService, authenticationFacade);

		this.centerService = centerService;
	}

	@GetMapping({ "/center" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{

		log.info("center");
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

	@PostMapping("/center/help-request")
	@ResponseBody
	public Result<WithusHelpRequest> postHelpRequest() {
		User user = getUser();
		LocalDateTime now = LocalDateTime.now();

		Result.Code code = Result.Code.ERROR;
		WithusHelpRequest withusHelpRequest = null;
		try {
			withusHelpRequest = centerService.createHelpRequest(user, now);
			code = Result.Code.OK;
		} catch (Exception exception) {
			log.error(exception.getLocalizedMessage(), exception);
		}

		return Result.<WithusHelpRequest>builder()
			.code(code)
			.data(withusHelpRequest)
			.build();
	}
}
