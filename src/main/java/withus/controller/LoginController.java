package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.dto.Result;
import withus.entity.User;
import withus.exception.UnexpectedEnumValueException;
import withus.service.UserService;
import withus.util.Utility;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping({ "/", "/login" })
	public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/login");

		return modelAndView;
	}

	@GetMapping({ "/registerUser" })
	public ModelAndView getRegisterPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/register");
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.addObject("previousUrl", "LogIn/login");

		return modelAndView;
	}


	@PostMapping(value = "/saveUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<User> putMember(@RequestBody User user) {

		User savedUser = null;
		Result.Code code = Result.Code.ERROR;

		if (!isMissingMandatories(user)) {
			try {
				User existId = userService.getUserById(user.getId());
				User existContact = userService.getUserByContact(user.getContact());

				if (existId == null && existContact == null) {
					User.Type userType = user.getType();

					switch (userType) {
						case CAREGIVER:
							savedUser = userService.upsertUserEncodingPassword(user);
							code = Result.Code.OK;
							break;
						case PATIENT:
							User caregiver = user.getCaregiver();
							if (caregiver == null) {
								savedUser = userService.upsertUserEncodingPassword(user);
								code = Result.Code.OK;
							} else {
								User existCareGiver = userService.getUserByContact(user.getCaregiver().getContact());
								if (existCareGiver == null) {
									code = Result.Code.ERROR_NO_EXIST_CAREGIVER;
								} else {
									user.setCaregiver(existCareGiver);
									savedUser = userService.upsertUserEncodingPassword(user);
									code = Result.Code.OK;
								}
							}
							break;
						default:
							throw new UnexpectedEnumValueException(userType, User.Type.class);
					}
				}

				if (existContact != null) {
					savedUser = existContact;
					code = Result.Code.ERROR_DUPLICATE_CONTACT;
				}
				if (existId != null) {
					savedUser = existId;
					code = Result.Code.ERROR_DUPLICATE_ID;
				}
			} catch (Exception exception) {
				logger.error(exception.getLocalizedMessage(), exception);
				code = Result.Code.ERROR_DATABASE;
			}
		}

		return Result.<User>builder()
			.setCode(code)
			.setData(savedUser)
			.createResult();
	}

	public boolean isMissingMandatories(User user){

		if (Utility.nullOrEmptyOrSpace(user.getId()) ||
			Utility.nullOrEmptyOrSpace(user.getPassword()) ||
			Utility.nullOrEmptyOrSpace(user.getName()) ||
			Utility.nullOrEmptyOrSpace(user.getContact())) {
			return true;
		}
		return false;
	}
}
