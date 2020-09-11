package withus.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.User;
import withus.entity.WithusHelpRequest;
import withus.service.CenterService;
import withus.service.UserService;
import withus.service.WwithusService;

@Controller
public class WwithusController extends BaseController {
	private final WwithusService wwithusService;
	private final CenterService centerService;

	@Autowired
	public WwithusController(UserService userService, AuthenticationFacade authenticationFacade, WwithusService wwithusService, CenterService centerService) {
		super(userService, authenticationFacade);

		this.wwithusService = wwithusService;
		this.centerService = centerService;
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
		LocalDate today = LocalDate.now();
		List<ChatBalloon> data = wwithusService.getWwithusEntryHistories(user, today);

		log.debug("User ({}) has {} history(ies) of today.", user, data.size());

		return Result.<List<ChatBalloon>>builder()
			.code(code)
			.data(data)
			.build();
	}

	@DeleteMapping("/wwithus/histories")
	@ResponseBody
	public Result.Code deleteHistories() {
		User user = getUser();
		LocalDate today = LocalDate.now();

		return wwithusService.deleteWwithusEntryHistories(user, today);
	}

	@PostMapping("/wwithus/request-next")
	@ResponseBody
	public Result<ChatBalloon> getNext(@RequestBody WwithusEntryRequest wwithusEntryRequest) {
		Result.Code code = Result.Code.ERROR;
		ChatBalloon data = null;

		try {
			User user = getUser();
			wwithusEntryRequest.setUser(user);
			wwithusEntryRequest.setDate(LocalDate.now());

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

	@PostMapping("/wwithus/help-request")
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
