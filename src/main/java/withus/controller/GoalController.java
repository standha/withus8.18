package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_main_button_count;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.UserService;

@Controller
public class GoalController extends BaseController {
    private final GoalService goalService;
    private final CountService countService;

    @Autowired
    public GoalController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, CountService countService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
        this.countService = countService;

    }

    @GetMapping("/goal")
    public ModelAndView getGoal() {
        ModelAndView modelAndView = new ModelAndView("goal/goal");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getGoal());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getGoal());

        return modelAndView;
    }

    @PutMapping(value = "/goal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }


    @GetMapping("/goallevel")
    public ModelAndView getGoallevel() {
        ModelAndView modelAndView = new ModelAndView("goal/goallevel");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getGoal());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getGoal());

        return modelAndView;
    }

    @PutMapping(value = "/goallevel", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoallevel(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @GetMapping("/goal1")
    public ModelAndView getGoal1() {
        ModelAndView modelAndView = new ModelAndView("goal/goal1");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getBottom_goals());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getBottom_goals());

        return modelAndView;
    }

    @PutMapping(value = "/goal1", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal1(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @GetMapping("/goal2")
    public ModelAndView getGoal2() {
        ModelAndView modelAndView = new ModelAndView("goal/goal2");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getMiddle_goals());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getMiddle_goals());

        return modelAndView;
    }

    @PutMapping(value = "/goal2", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal2(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }


    @GetMapping("/goal3")
    public ModelAndView getGoal3() {
        ModelAndView modelAndView = new ModelAndView("goal/goal3");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getTop_goals());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getTop_goals());

        return modelAndView;
    }

    @PutMapping(value = "/goal3", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal3(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @GetMapping("/goal0")
    public ModelAndView getGoal0() {
        ModelAndView modelAndView = new ModelAndView("goal/goal0");
        User user = getUser();
        User.Type typeCheck = user.getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goal", goal.getGoal());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getGoal());

        return modelAndView;
    }

    @PutMapping(value = "/goal0", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal0(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setGoalId(user.getUserId());
        Result.Code code;
        Tbl_goal saved = null;

        try {
            if (user.getType() == User.Type.PATIENT) {
                saved = goalService.upsertGoal(tbl_goal);
                code = Result.Code.OK;
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_goal>builder()
                .code(code)
                .data(saved)
                .build();
    }

}
