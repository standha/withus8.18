package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.ExerciseService;
import withus.service.GoalService;
import withus.service.UserService;

import java.time.LocalDate;

@Controller
public class GoalController extends BaseController {
    private final GoalService goalService;

    @Autowired
    public GoalController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
    }

    @GetMapping("/goal")
    @Statistical
    public ModelAndView getGoal() {
        ModelAndView modelAndView = new ModelAndView("goal/goal");
        User.Type typeCheck = getUser().getType();
        Tbl_goal goal = goalService.getGoalId(getConnectId());
        modelAndView.addObject("goal", goal.getGoal());
        modelAndView.addObject("type",typeCheck);
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }
    @PutMapping(value = "/goal",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getMoisture(@RequestBody Tbl_goal tbl_goal){
        tbl_goal.setGoalId(getConnectId());
        Result.Code code;
        Tbl_goal saved = null;
        try{
            saved = goalService.upsertGoal(tbl_goal);
            code = Result.Code.OK;
        }catch (Exception exception){
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_goal>builder()
                .setCode(code)
                .setData(saved)
                .createResult();
    }
}
