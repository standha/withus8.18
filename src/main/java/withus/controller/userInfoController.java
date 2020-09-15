package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.User;
import withus.exception.UnexpectedEnumValueException;
import withus.service.UserService;
import withus.util.Utility;


@Controller
public class userInfoController extends BaseController {
    @Autowired
    public userInfoController(AuthenticationFacade authenticationFacade, UserService userService){
        super(userService, authenticationFacade);
    }

    @GetMapping("/changeInfo")
    public ModelAndView getUserInfo() {
        ModelAndView modelAndView = new ModelAndView("/changeInfo");
        User user = getUser();
        if(user.getType() == User.Type.PATIENT && user.getCaregiver() != null){
            modelAndView.addObject("caregiver_contact", user.getCaregiver().getContact());
        }else if(user.getType() == User.Type.PATIENT && user.getCaregiver() == null){
            modelAndView.addObject("caregiver_contact", "현재 보호자 번호가 없습니다.");
        }else{
            modelAndView.addObject("caregiver_contact", "현재 보호자 번호가 없습니다..");
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }
    @PostMapping(value = "/changeInfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<User> putMember(@RequestBody User user) {
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
                                } else {
                                    user.setCaregiver(existCareGiver);
                                    savedUser = userService.upsertUser(user);
                                    code = Result.Code.OK;
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
                .setCode(code)
                .setData(savedUser)
                .createResult();
    }
    public boolean isMissingMandatories(User user){

        if (Utility.nullOrEmptyOrSpace(user.getUserId()) ||
                Utility.nullOrEmptyOrSpace(user.getPassword()) ||
                Utility.nullOrEmptyOrSpace(user.getName()) ||
                Utility.nullOrEmptyOrSpace(user.getContact())) {
            return true;
        }
        return false;
    }
}
