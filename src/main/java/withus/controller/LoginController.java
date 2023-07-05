package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping({"/", "/login"})
    public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("LogIn/login");

        return modelAndView;
    }

    @GetMapping({"/registerUser"})
    public ModelAndView getRegisterPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String token) {
        ModelAndView modelAndView = new ModelAndView("LogIn/register");
        User user = new User();
        modelAndView.addObject("appToken", token);
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
                logger.info("try saveUser id:{}", user.getUserId());

                User existId = userService.getUserById(user.getUserId());

                logger.info("existId:{}", existId);

                User existContact = userService.getUserByContact(user.getContact());

                logger.info("existContact:{}", existContact);

                if (existId == null && existContact == null) {
                    User.Type userType = user.getType();

                    logger.info("save info type:{}", userType);

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

                                logger.error("code:{}", code);
                            } else {
                                User existCareGiver = userService.getUserByContact(user.getCaregiver().getContact());
                                if (existCareGiver == null) {
                                    code = Result.Code.ERROR_NO_EXIST_CAREGIVER;

                                    logger.info("code:{}", code);
                                } else if (user.getContact().equals(user.getCaregiver().getContact())){
                                    code = Result.Code.ERROR_SELF_REFERENCE;
                                    logger.info("code:{}", code);
                                } else if(existCareGiver.getType().equals(User.Type.PATIENT)){
                                    code = Result.Code.ERROR_PATIENT_REFERENCE;
                                    logger.info("code:{}", code);
                                } else {
                                    user.setCaregiver(existCareGiver);
                                    savedUser = userService.upsertUserEncodingPassword(user);
                                    code = Result.Code.OK;
                                    logger.info("code:{}", code);
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

                    logger.info("code:{}", code);
                }

                if (existId != null) {
                    savedUser = existId;
                    code = Result.Code.ERROR_DUPLICATE_ID;

                    logger.info("code:{}", code);
                }
            } catch (Exception exception) {
                logger.error(exception.getLocalizedMessage(), exception);
                code = Result.Code.ERROR_DATABASE;

                logger.error("code:{}", code);
            }
        }

        return Result.<User>builder()
                .code(code)
                .data(savedUser)
                .build();
    }

    public boolean isMissingMandatories(User user) {
        if (Utility.nullOrEmptyOrSpace(user.getUserId()) ||
                Utility.nullOrEmptyOrSpace(user.getPassword()) ||
                Utility.nullOrEmptyOrSpace(user.getName()) ||
                Utility.nullOrEmptyOrSpace(user.getContact())) {

            return true;
        }

        return false;
    }
}
