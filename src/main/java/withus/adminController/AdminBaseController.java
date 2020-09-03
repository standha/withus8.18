package withus.adminController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.service.UserService;

public class AdminBaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final UserService userService;
	private final AuthenticationFacade authenticationFacade;

	public AdminBaseController(UserService userService, AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
		this.userService = userService;
	}

	protected Authentication getAuthentication() {
		return authenticationFacade.getAuthentication();
	}
	protected String getUsername() {
		return authenticationFacade.getAuthentication().getName();
	}
	protected User getUser() {
		return userService.getUserById(getUsername());
	}
	protected String getPatientContact() {
		return getUser().getContact();
	}

}
