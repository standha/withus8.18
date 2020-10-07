package withus.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.querydsl.core.Tuple;
import com.google.gson.Gson;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.AllUserDTO;
import withus.entity.*;
import withus.entity.User.Type;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.HelperRequestService;
import withus.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class CenterController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final GoalService goalService;
    private final CountService countService;
    private final HelperRequestService helperRequestService;

    @Autowired
    public CenterController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, CountService countService, HelperRequestService helperRequestService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
        this.countService = countService;
        this.helperRequestService = helperRequestService;
    }

    @GetMapping({"/center"})
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String token) {
        User user = getUser();
        logger.trace("id:{}, url:{}, type:{}, level:{}, week:{}", user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek());
        if (user.getAppToken() != null) {
            if (token != null) {
                if (user.getAppToken().equals(token) == false) {
                    Result.Code code;
                    user.setAppToken(token);
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

        ModelAndView modelAndView = new ModelAndView();
        if (user.getType().equals(Type.ADMINISTRATOR)) {
            List<AllUserDTO> resultList = new ArrayList<>();
            ArrayList<String> userFin = userService.getAllUserPlz();
            for (String aUserFin : userFin) {
                resultList.add(AllUserDTO.fromString(aUserFin));
            }

            logger.debug("user:{}", userFin);

            modelAndView.addObject("user", resultList);
            modelAndView.setViewName("/Admin/admin_Home");
        } else if (user.getType().equals(Type.PATIENT)) { //환자 로그인 중
            if (user.getWeek() == 0) {
                modelAndView.setViewName("home_0week");
            } else {
                Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.setViewName("home");
                modelAndView.addObject("count", count);
                modelAndView.addObject("type", user.getType());
                modelAndView.addObject("week", user.getWeek());
            }
        } else {    //보호자 로그인 중
            if (getCaretaker().getWeek() == 0) {
                modelAndView.setViewName("home_0week");
            } else {
                modelAndView.setViewName("home");
                modelAndView.addObject("type", user.getType());
                modelAndView.addObject("week", getCaretaker().getWeek());
            }
        }

        if (user.getType() == Type.CAREGIVER || user.getType() == Type.PATIENT) {
            modelAndView.addObject("goalNow", getGoalNow(getConnectId()));
            modelAndView.addObject("level", ViewLevel(user));
            modelAndView.addObject("user", user);
        }

        List<AllUserDTO> resultList = new ArrayList<>();
        ArrayList<String> userFin = userService.getAllUserPlz();

        for (String aUserFin : userFin) {
            resultList.add(AllUserDTO.fromString(aUserFin));
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

    public String getGoalNow(String username) {
        Integer goalCheck = goalService.getGoalId(username).getGoal();
        String goalNow = "";

        switch (goalCheck) {
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
        }
        return goalNow;
    }
}
