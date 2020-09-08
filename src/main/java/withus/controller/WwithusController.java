package withus.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.ChatBalloon;
import withus.entity.User;
import withus.entity.WwithusEntryHistory;
import withus.service.UserService;
import withus.service.WwithusService;

@Controller
@Slf4j
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

	@GetMapping("/wwithus/chat-balloons")
	@ResponseBody
	public Result<List<ChatBalloon>> getChatBalloons() {
		User user = getUser();
		Result.Code code = Result.Code.OK;
		List<ChatBalloon> data = null;
		List<WwithusEntryHistory> wwithusEntryHistories = wwithusService.getWwithusEntryHistories(user);
		if (!wwithusEntryHistories.isEmpty()) {
			log.debug("User ({}) has {} history(ies) of today.", user, wwithusEntryHistories.size());
			data = wwithusService.toChatBalloons(wwithusEntryHistories);
		}

		return Result.<List<ChatBalloon>>builder()
			.code(code)
			.data(data)
			.build();
	}
}
