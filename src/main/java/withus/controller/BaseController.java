package withus.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    public BaseController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    protected Authentication getAuthentication() {
        return authenticationFacade.getAuthentication();
    }

    protected String getUsername() {
        return getAuthentication().getName();
    }

    protected User getUser() {
        return userService.getUserById(getUsername());
    }

    protected User getUserAndDate() {
        return userService.getUserByIdAndDate(getUsername());
    }

    protected String getPatientContact() {
        return getUser().getContact();
    }

    protected List<User> getAllPatient() {
        return userService.getAllPatient();
    }

    @Nullable
    protected User getCaretaker() {
        return userService.getUserByCaregiverId(getUsername());
    }

    protected String getConnectId() {
        String username = null;

        switch (getUser().getType()) {
            case PATIENT:
                username = getUsername();
                break;
            case CAREGIVER:
                username = getCaretaker().getUserId();
                break;
        }

        return username;
    }


}
