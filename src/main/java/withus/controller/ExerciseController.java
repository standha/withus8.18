package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.service.ExerciseService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExerciseController extends BaseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(AuthenticationFacade authenticationFacade, UserService userService, ExerciseService exerciseService){
        super(userService, authenticationFacade);
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercise")
    @Statistical
    public ModelAndView getExercise() {
        ModelAndView modelAndView = new ModelAndView("exercise/exercise");
        String user = getUsername();
        modelAndView.addObject("previousUrl", "/home");
        System.out.println("UserName : "+user);
        return modelAndView;
    }
    @PostMapping("/exercise")
    @ResponseBody
    public Result<Tbl_Exercise_record> PostPatientVisit(@RequestBody Tbl_Exercise_record tbl_exercise_record){
        String userId = getUsername();
        tbl_exercise_record.setPk(new RecordKey(userId, LocalDate.now()));
        Result.Code code;
        Tbl_Exercise_record seved = null;
        try{
            seved = exerciseService.upsertExerciseRecord(tbl_exercise_record);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_Exercise_record>builder()
                .setCode(code)
                .setData(seved)
                .createResult();
    }
    @GetMapping("/exercise-all-history")
    @Statistical
    public ModelAndView getExerciseAll(){
        ModelAndView modelAndView = new ModelAndView("exercise/exercise-all-history");
        String username = getUsername();
        List<Tbl_Exercise_record> exerciseHistory;
        exerciseHistory = exerciseService.getExerciseAllRecord(username,-1, -1);
        modelAndView.addObject("exercise",exerciseHistory);
        modelAndView.addObject("previousUrl","exercise");
        return modelAndView;
    }
}
