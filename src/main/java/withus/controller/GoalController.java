package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.GoalService;
import withus.service.GoaloptionService;
import withus.service.UserService;

@Controller
public class GoalController extends BaseController {
    private final GoalService goalService;
    private final GoaloptionService goaloptionService;
    private final CountService countService;

    @Autowired
    public GoalController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, GoaloptionService goaloptionService, CountService countService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
        this.goaloptionService = goaloptionService;
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
        Tbl_goal goal = goalService.getGoalId(user.getUserId());

        Tbl_patient_main_button_count count = countService.getPatientMainCount(
                new ProgressKey(user.getUserId(), user.getWeek()));



        modelAndView.addObject("count", count);
        modelAndView.addObject("goalList", goal);
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}", user.getUserId());

        return modelAndView;
    }

    @PutMapping(value = "/goal1", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal1(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        tbl_goal.setWeek(user.getWeek());
        //tbl_goal.setGoalId(getConnectId());
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
        Tbl_goal goal = goalService.getGoalId(user.getUserId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goalList", goal);
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}", user.getUserId());

        return modelAndView;
    }

    @PutMapping(value = "/goal2", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal2(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        //tbl_goal.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_goal.setWeek(user.getWeek());
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
        Tbl_goal goal = goalService.getGoalId(user.getUserId());

        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goalList", goal);
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goal:{}", user.getUserId(), goal.getBottom_goals());

        return modelAndView;
    }

    @PutMapping(value = "/goal3", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal3(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        //tbl_goal.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_goal.setWeek(user.getWeek());
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
        Tbl_goal goal = goalService.getGoalId(user.getUserId());
        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));

        modelAndView.addObject("count", count);
        modelAndView.addObject("goaloption", goal);
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        logger.info("id:{}, goaloption:{}", user.getUserId(), goal.getWeek());

        return modelAndView;
    }

    @PostMapping(value = "/goal0", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal0(@RequestBody Tbl_goal tbl_goal) {
        User user = getUser();
        //tbl_goal.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_goal.setWeek(user.getWeek());
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






//    @GetMapping("/goal0")
//    public ModelAndView getGoal0() {
//        ModelAndView modelAndView = new ModelAndView("goal/goal0");
//        User user = getUser();
//        User.Type typeCheck = user.getType();
//        Tbl_goaloption goal = goaloptionService.getGoalId(getConnectId());
//        Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
//
//        modelAndView.addObject("count", count);
//        modelAndView.addObject("goal", goal.getGoaloption());
//        modelAndView.addObject("type", typeCheck);
//        modelAndView.addObject("week", user.getWeek());
//        modelAndView.addObject("previousUrl", "/center");
//
//        logger.info("id:{}, goaloption:{}", user.getUserId(), goal.getGoaloption());
//
//        return modelAndView;
//    }
//
//    @PutMapping(value = "/goal0", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public Result<Tbl_goaloption> getGoal0(@RequestBody Tbl_goaloption tbl_goaloption) {
//        User user = getUser();
//        //tbl_goal.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
//        tbl_goaloption.setWeek(user.getWeek());
//        tbl_goaloption.setGoalId(user.getUserId());
//        Result.Code code;
//        Tbl_goaloption saved = null;
//
//        try {
//            if (user.getType() == User.Type.PATIENT) {
//                saved = goaloptionService.upsertGoal(tbl_goaloption);
//                code = Result.Code.OK;
//            } else {
//                throw new IllegalStateException("Caregiver try input data [warn]");
//            }
//        } catch (Exception exception) {
//            logger.error(exception.getLocalizedMessage(), exception);
//            code = Result.Code.ERROR_DATABASE;
//        }
//
//        return Result.<Tbl_goaloption>builder()
//                .code(code)
//                .data(saved)
//                .build();
//    }

}
