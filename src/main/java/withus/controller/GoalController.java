package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_goal;
import withus.entity.User;
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
        if(goalService.getGoalId(getConnectId()) == null){
            modelAndView.addObject("goal", null);
        }else{
            Tbl_goal goal = goalService.getGoalId(getConnectId());
            modelAndView.addObject("goal", goal.getGoal());
        }
        modelAndView.addObject("type",typeCheck);
        modelAndView.addObject("previousUrl", "/home");
        return modelAndView;
    }
}
