package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public GoalController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
    }

    @GetMapping("/goal")
    @Statistical
    public ModelAndView getGoal(){
        ModelAndView modelAndView = new ModelAndView("goal/goal");
        modelAndView.addObject("previousUrl", "/center");

        modelAndView.addObject("type", getUser().getType());
        return modelAndView;
    }

//    팝업창 사용한다면..
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
        tbl_goal.setGoalId(userId);
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
