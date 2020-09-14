package withus.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.RecordKey;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.GoalService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GoalController extends BaseController{
    private final GoalService goalService;

    public GoalController(UserService userService, AuthenticationFacade authenticationFacade, GoalService goalService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
    }

    @GetMapping("/goal")
    @Statistical
    public ModelAndView getGoal(){
        ModelAndView modelAndView = new ModelAndView("goal/goal");
        modelAndView.addObject("previousUrl", "/home");

        Tbl_goal goalRecord;
        String userId = getUsername();
        String temp = null;

        goalRecord = goalService.getGoalDateRecord(new RecordKey(userId, LocalDate.now()), 0);
        if(goalRecord == null){
            temp = "목표를 설정해 주세요";
        }
        else {
            if (goalRecord.getGoal() == 1) {
                temp = "목표 1설정";
            } else if (goalRecord.getGoal() == 2) {
                temp = "목표 2설정";
            }
        }
        modelAndView.addObject("goalThisWeek", temp);
        return modelAndView;
    }
//
//    @GetMapping("/poptest")
//    @Statistical
//    public ModelAndView poptest(){
//        ModelAndView modelAndView = new ModelAndView("goal/poptest");
//
//        return modelAndView;
//    }

    @PostMapping(value = "/goal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_goal> getGoal(@RequestBody Tbl_goal tbl_goal){
        String userId = getUsername();
        tbl_goal.setPk(new RecordKey(userId, LocalDate.now()));
        Result.Code code;
        Tbl_goal saved = null;
        try{
            saved = goalService.upsertGoal(tbl_goal);
            code = Result.Code.OK;
        }catch(Exception exception){
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_goal>builder()
                .setCode(code)
                .setData(saved)
                .createResult();
    }
}
