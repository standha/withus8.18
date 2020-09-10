package withus.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.User;
import withus.service.UserService;
import withus.service.WwithusService;

@Controller
public class WwithusController extends BaseController {
	private final WwithusService wwithusService;

	@Autowired
	public WwithusController(UserService userService, AuthenticationFacade authenticationFacade, WwithusService wwithusService) {
		super(userService, authenticationFacade);

		this.wwithusService = wwithusService;
	}

	@GetMapping("/wwithus")
	public ModelAndView getWwithus() {
		ModelAndView modelAndView = new ModelAndView("wwithus/wwithus.html");
		modelAndView.addObject("previousUrl", "/home");

		return modelAndView;
	}

	@GetMapping("/wwithus/histories")
	@ResponseBody
	public Result<List<ChatBalloon>> getHistories() {
		Result.Code code = Result.Code.OK;

		User user = getUser();
		List<ChatBalloon> data = wwithusService.getWwithusEntryHistories(user);

		log.debug("User ({}) has {} history(ies) of today.", user, data.size());

		return Result.<List<ChatBalloon>>builder()
			.code(code)
			.data(data)
			.build();
	}

	@GetMapping("/wwithus/request-next")
	@ResponseBody
	public Result<ChatBalloon> getNext(@RequestParam(name = "nextCode", required = false) String nextCode) {
		Result.Code code = Result.Code.ERROR;
		ChatBalloon data = null;

		try {
			User user = getUser();
			WwithusEntryRequest wwithusEntryRequest = WwithusEntryRequest.builder()
				.user(user)
				.nextCode(nextCode)
				.date(LocalDate.of(2020, 9, 7))
				.build();

			data = wwithusService.getWwithusEntryAndSaveHistory(wwithusEntryRequest);
			code = Result.Code.OK;
		} catch (Exception exception) {
			log.error(exception.getLocalizedMessage(), exception);
		}

		return Result.<ChatBalloon>builder()
			.code(code)
			.data(data)
			.build();
	}
}
