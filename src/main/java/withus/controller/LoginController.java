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

                user.setTempContact(null);
                if(user.getType() ==User.Type.CAREGIVER && user.getRelative() == User.Relative.NONE){

                }


                if (existId == null && existContact == null ) {
                    User.Type userType = user.getType();

                    logger.info("save info type:{}", userType);

                    switch (userType) {
                        case CAREGIVER:
                            User patient = user.getCaregiver();
                            if (patient == null) {
                                code = Result.Code.NO_INPUT_PATIENT;
                                logger.error("code:{}", code);
                            } else {
                                User existPatient = userService.getUserByContact(user.getCaregiver().getContact());

                                logger.info("existPatient:{}", existPatient);
                                if (existPatient == null) {
                                    User tempUser =  userService.getUserTopTempContact(user.getContact());
                                    if(tempUser != null && user.getCaregiver().getContact().equals(tempUser.getContact())){
                                        savedUser = userService.upsertUserEncodingPassword(user);
                                        code = Result.Code.OK;
                                        logger.info("code:{}", code);
                                    } else if(tempUser == null){
                                        user.setTempContact(user.getCaregiver().getContact());
                                        user.setCaregiver(null);
                                        savedUser = userService.upsertUserEncodingPassword(user);

                                        code = Result.Code.OK;
                                        logger.info("code:{}", code);
                                    } else {
                                        code = Result.Code.ERROR_CAREGIVER_TEMP_CONTACT;
                                        logger.info("code:{}", code);
                                    }

                                } else if (user.getContact().equals(user.getCaregiver().getContact())){
                                    code = Result.Code.ERROR_SELF_REFERENCE;
                                    logger.info("code:{}", code);
                                } else if(existPatient.getType().equals(User.Type.CAREGIVER)){
                                    code = Result.Code.ERROR_CAREGIVER_REFERENCE;
                                    logger.info("code:{}", code);
                                } else if(user.getRelative() == User.Relative.NONE){
                                    code = Result.Code.EROR_RELATION;
                                    logger.info("code:{}", code);
                                } else if(existPatient.getTempContact() != null){
                                    if(existPatient.getTempContact().equals(user.getContact())){
                                        existPatient.setTempContact(null);
                                        userService.updateTempContactInfo(existPatient);

                                        user.setCaregiver(null);
                                        existPatient.setCaregiver(user);
                                        savedUser = userService.upsertUserEncodingPassword(user);
                                        userService.updateCaregiverInfo(existPatient);
                                        code = Result.Code.OK;
                                        logger.info("code:{}", code);

                                    } else {
                                        code = Result.Code.ALREADY_CONTACT_EXIST;
                                        logger.info("code:{}", code);
                                    }
                                } else {
                                    user.setCaregiver(null);
                                    existPatient.setCaregiver(user);
                                    savedUser = userService.upsertUserEncodingPassword(user);
                                    userService.updateCaregiverInfo(existPatient);
                                    code = Result.Code.OK;
                                    logger.info("code:{}", code);
                                }
                            }
                            break;

                        case PATIENT:
                            User caregiver = user.getCaregiver();
                            User tempUser =  userService.getUserTopTempContact(user.getContact());
                            if (caregiver == null) {
                                User existCaregiverContact = userService.getUserTopByCaregiverContact(user.getContact());
                                savedUser = userService.upsertUserEncodingPassword(user);
                                code = Result.Code.OK;
                                logger.error("code:{}", code);
                                if(existCaregiverContact != null){
                                    user.setCaregiver(existCaregiverContact);
                                    userService.updateCaregiverInfo(user);
                                    existCaregiverContact.setCaregiver(null);
                                    userService.updateCaregiverInfo(existCaregiverContact);
                                }
                                if(tempUser != null){
                                    tempUser.setTempContact(null);
                                    user.setCaregiver(tempUser);
                                    userService.updateTempContactInfo(tempUser);
                                    userService.updateCaregiverInfo(user);
                                }
                            } else {
                                User existCareGiver = userService.getUserByContact(caregiver.getContact());
                                logger.info("existCareGiver:{}", existCareGiver);

                                if (existCareGiver == null) {
                                    if(tempUser != null && caregiver.getContact().equals(tempUser.getContact())){
                                        user.setTempContact(caregiver.getContact());
                                        user.setCaregiver(null);
                                        savedUser = userService.upsertUserEncodingPassword(user);
                                        code = Result.Code.OK;
                                        logger.info("code:{}", code);
                                    } else if(tempUser == null){
                                        user.setTempContact(user.getCaregiver().getContact());
                                        user.setCaregiver(null);
                                        savedUser = userService.upsertUserEncodingPassword(user);
                                        code = Result.Code.OK;
                                        logger.info("code:{}", code);
                                    } else {
                                        code = Result.Code.ERROR_PATIENT_TEMP_CONTACT;
                                        logger.info("code:{}", code);
                                    }

                                } else if (user.getContact().equals(user.getCaregiver().getContact())){
                                    code = Result.Code.ERROR_SELF_REFERENCE;
                                    logger.info("code:{}", code);
                                } else if(existCareGiver.getType().equals(User.Type.PATIENT)){
                                    code = Result.Code.ERROR_PATIENT_REFERENCE;
                                    logger.info("code:{}", code);
                                } else if(existCareGiver.getCaregiver() != null && !existCareGiver.getCaregiver().getContact().equals(user.getContact())){
                                    code = Result.Code.ALREADY_CONTACT_EXIST;
                                    logger.info("code:{}", code);
                                } else {
                                    existCareGiver.setTempContact(null);
                                    userService.updateTempContactInfo(existCareGiver);
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
