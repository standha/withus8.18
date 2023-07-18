package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_main_button_count;
import withus.entity.User;
import withus.exception.UnexpectedEnumValueException;
import withus.service.CountService;
import withus.service.UserService;
import withus.util.Utility;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserInfoController extends BaseController {
    private final CountService countService;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserInfoController(AuthenticationFacade authenticationFacade, UserService userService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }
/*

    @GetMapping("/user/{userId}")
    public ModelAndView aaa(@PathVariable("userId") String userId) {
        // @pathVariable, @ParameterValue, @Header에
    }
*/

    @GetMapping("/changeInfo")
    public ModelAndView getUserInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("changeInfo");
        User user = getUser();
        if (user.getType() == User.Type.PATIENT && user.getCaregiver() != null) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
            modelAndView.addObject("caregiver_contact", user.getCaregiver().getContact());

            logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, gender:{}, name:{}, contact:{}, caregiver_contact:{}, birthdate:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), user.getGender(), user.getName(), user.getContact(), user.getCaregiver().getContact(), user.getBirthdate());
        } else if (user.getType() == User.Type.PATIENT && user.getCaregiver() == null) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
            modelAndView.addObject("caregiver_contact", null);

            logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, gender:{}, name:{}, contact:{}, caregiver_contact:{}, birthdate:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), user.getGender(), user.getName(), user.getContact(), null, user.getBirthdate());
        } else {
            modelAndView.addObject("caregiver_contact", null);

            logger.info("id:{}, url:{}, type:{}, name:{}, contact:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getName(), user.getContact());
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }

    @PostMapping(value = "/changeInfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> putMember(@RequestBody User user, HttpServletRequest request) {
        if (user.getType() == User.Type.PATIENT && user.getCaregiver() != null) {
            logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, gender:{}, name:{}, contact:{}, caregiver_contact:{}, birthdate:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), user.getGender(), user.getName(), user.getContact(), user.getCaregiver().getContact(), user.getBirthdate());
        } else if (user.getType() == User.Type.PATIENT && user.getCaregiver() == null) {
            logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, gender:{}, name:{}, contact:{}, caregiver_contact:{}, birthdate:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), user.getGender(), user.getName(), user.getContact(), null, user.getBirthdate());
        } else {
            logger.info("id:{}, url:{}, type:{}, name:{}, contact:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getName(), user.getContact());
        }

        User savedUser = null;
        Result.Code code = Result.Code.ERROR;

        if (!isMissingMandatories(user)) {
            try {
                User.Type userType = user.getType();
                switch (userType) {
                    case CAREGIVER:
                        savedUser = userService.upsertUser(user);
                        code = Result.Code.OK;
                        break;
                    case PATIENT:
                        User caregiver = user.getCaregiver();
                        if (caregiver == null) {
                            savedUser = userService.upsertUser(user);
                            code = Result.Code.OK;
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
                                savedUser = userService.upsertUser(user);
                                code = Result.Code.OK;

                                logger.info("code:{}", code);
                            }
                        }
                        break;
                    default:
                        throw new UnexpectedEnumValueException(userType, User.Type.class);
                }
            } catch (Exception exception) {
                logger.error(exception.getLocalizedMessage(), exception);

                code = Result.Code.ERROR_DATABASE;
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
