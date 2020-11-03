package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
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
        Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));

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
}
