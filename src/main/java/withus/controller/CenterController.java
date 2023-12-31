package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.*;
import withus.entity.*;
import withus.entity.User.Type;
import withus.service.AdminService;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CenterController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final GoalService goalService;
    private final CountService countService;

    private final AdminService adminService;

    @Autowired
    public CenterController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, CountService countService, AdminService adminService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
        this.countService = countService;
        this.adminService = adminService;
    }

    @GetMapping({"/center"})
    public ModelAndView getMain(HttpServletRequest request, @RequestParam(required = false) String token) {
        User user = getUserAndDate();
        ModelAndView modelAndView = new ModelAndView();

        logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, userRecordDate:{}", user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), user.getUserRecordDate());

        if ((user.getType() == Type.CAREGIVER) && (getCaretaker() == null)) {
            modelAndView.addObject("error", true);
            modelAndView.setViewName("LogIn/login");

            logger.info("id:{}, url:{}, type:{}", user.getUserId(), request.getRequestURL(), user.getType());
        } else {
            if (user.getAppToken() != null) {
                if (token != null) {
                    if (user.getAppToken().equals(token) == false) {
                        Result.Code code;
                        user.setAppToken(token);

                        logger.info("id:{}, type:{}, appToken change, appToken:{}", user.getUserId(), user.getType(), user.getAppToken());

                        try {
                            user = userService.upsertUser(user);
                            code = Result.Code.OK;
                        } catch (Exception exception) {
                            logger.error(exception.getLocalizedMessage(), exception);
                            code = Result.Code.ERROR_DATABASE;
                        }

                        Result.<User>builder()
                                .code(code)
                                .data(user)
                                .build();
                    }
                }
            } else {
                if (token != null) {
                    Result.Code code;
                    user.setAppToken(token);

                    logger.info("id:{}, type:{}, appToken change, appToken:{}", user.getUserId(), user.getType(), user.getAppToken());

                    try {
                        user = userService.upsertUser(user);
                        code = Result.Code.OK;
                    } catch (Exception exception) {
                        logger.error(exception.getLocalizedMessage(), exception);
                        code = Result.Code.ERROR_DATABASE;
                    }

                    Result.<User>builder()
                            .code(code)
                            .data(user)
                            .build();
                }
            }
            // todo: 관리자 로그인 페이지 분리
            if (user.getType().equals(Type.ADMINISTRATOR)) {
                List<UserGenderCountDTO> userGenderCountInfo = adminService.getUserGenderCountInfo();
                List<UserAgeCountDTO> userAgeCountInfo = adminService.getUserAgeCountInfo();
                List<UserRegisterCountDTO> userRegisterCountInfo = adminService.getUserRegisterCountInfo();
                List<UserWeekCountDTO> userWeekCountInfo = adminService.getUserWeekCountInfo();
                List<UserRelativeCountDTO> userRelativeCountInfo = adminService.getUserRelativeCountInfo();
                List<CaregiverButtonSumDTO> caregiverButtonSumInfo = adminService.getCaregiverButtonSumInfo();
                List<PatientButtonSumDTO> patientButtonSumInfo = adminService.getPatientButtonSumInfo();

                modelAndView.addObject("admin", user.getUserId());
                modelAndView.addObject("userGenderCountList",userGenderCountInfo);
                modelAndView.addObject("userAgeCountList",userAgeCountInfo);
                modelAndView.addObject("userRegisterCountList",userRegisterCountInfo);
                modelAndView.addObject("userWeekCountList",userWeekCountInfo);
                modelAndView.addObject("userRelativeCountList",userRelativeCountInfo);
                modelAndView.addObject("caregiverButtonSumList",caregiverButtonSumInfo);
                modelAndView.addObject("patientButtonSumList",patientButtonSumInfo);

                modelAndView.setViewName("Admin/admin_dashboard");

            } else if (user.getType().equals(Type.PATIENT)) { //환자 로그인 중
                if (user.getWeek() == 0) {
                    modelAndView.setViewName("home_0week");
                } else {
                    Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
                    modelAndView.setViewName("home");
                    modelAndView.addObject("count", count);
                    modelAndView.addObject("type", user.getType());
                    modelAndView.addObject("week", user.getWeek());
                }
            } else {    //보호자 로그인 중
                if (getCaretaker().getWeek() == 0) {
                    modelAndView.setViewName("home_0week");
                } else {
                    Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
                    modelAndView.setViewName("home");
                    modelAndView.addObject("count", count);
                    modelAndView.addObject("type", user.getType());
                    modelAndView.addObject("week", getCaretaker().getWeek());
                }
            }

            if (user.getType() == Type.CAREGIVER || user.getType() == Type.PATIENT) {
                //modelAndView.addObject("goalNow", getGoalNow(new RecordKey(user.getUserId(), LocalDate.now())));  //8월 2일 수정
                modelAndView.addObject("goalNow", getGoalNow(getConnectId()));
                modelAndView.addObject("level", ViewLevel(user));
                modelAndView.addObject("user", user);
            }
        }

        return modelAndView;
    }


    public Integer ViewLevel(User user) {
        Integer level = 1;

        switch (user.getType()) {
            case PATIENT:
                level = user.getLevel();
                level = level % 4;
                break;
            case CAREGIVER:
                level = getCaretaker().getLevel();
                level = level % 4;
                break;
        }

        return level;
    }

    public String getGoalNow(String name) {
        String goalCheck = goalService.getGoalId(name).getGoal();   //8월 2일 수
        //Integer goalCheck = goalService.getGoalId(name).getGoal();   //8월 2일 수정
        String goalNow = null;
        //String goalNow = "";
        if (goalCheck == "0"){
            goalNow = "이번주 목표를 설정해봐요!";
        }else{
            goalNow=goalCheck;
        }

        /*switch (goalCheck) {
            case 0:
                goalNow = "이번주 목표를 설정해봐요!";
                break;
            case 1:
                goalNow = "매일 정해진 시간 약 복용";
                break;
            case 2:
                goalNow = "매일 혈압과 맥박 측정";
                break;
            case 3:
                goalNow = "매일 체중 측정";
                break;
            case 4:
                goalNow = "주 3회 이상 증상일지 기록";
                break;
            case 5:
                goalNow = "매일 증상일지 기록";
                break;
            case 6:
                goalNow = "주 3회 이상 식사 시 염분 측정";
                break;
            case 7:
                goalNow = "매일 식사 시 염분 측정";
                break;
            case 8:
                goalNow = "주 1회, 최소 30분 이상 운동";
                break;
            case 9:
                goalNow = "주 3회, 최소 30분 이상 운동";
                break;
        }*/

        return goalNow;
    }
}
