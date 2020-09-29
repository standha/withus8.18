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
import withus.entity.Tbl_symptom_log;
import withus.entity.User;
import withus.service.ExerciseService;
import withus.service.UserService;

import java.time.DayOfWeek;
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
        ModelAndView modelAndView = new ModelAndView("exercise/exercise_");
        User.Type typeCheck = getUser().getType();
        switch (typeCheck){
            case PATIENT:
                if(exerciseService.getExercise(new RecordKey(getUsername(), LocalDate.now()))==null){
                    modelAndView.addObject("hour", "");
                    modelAndView.addObject("minute", "");
                }else{
                    Tbl_Exercise_record exercise = exerciseService.getExercise(new RecordKey(getUsername(), LocalDate.now()));
                    modelAndView.addObject("hour", exercise.getHour());
                    modelAndView.addObject("minute", exercise.getMinute());
                }
                break;
            case CAREGIVER:
                if(exerciseService.getExercise(new RecordKey(getCaretaker().getUserId(), LocalDate.now()))==null){
                    modelAndView.addObject("hour", "");
                    modelAndView.addObject("minute", "");
                }else{
                    Tbl_Exercise_record exercise = exerciseService.getExercise(new RecordKey(getCaretaker().getUserId(), LocalDate.now()));
                    modelAndView.addObject("hour", exercise.getHour());
                    modelAndView.addObject("minute", exercise.getMinute());
                }
                break;
        }
        modelAndView.addObject("type",typeCheck);
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }
    @GetMapping("/exercise-all-history")
    @Statistical
    public ModelAndView getExerciseAll(){
        ModelAndView modelAndView = new ModelAndView("exercise/exercise-all-history");
        String username = getUsername();
        List<Tbl_Exercise_record> exerciseHistory;
        switch (getUser().getType()){
            case PATIENT:
                exerciseHistory = exerciseService.getExerciseAllRecord(username,-1, -1);
                modelAndView.addObject("exerciseWeekHour",avgWeek()/60);
                modelAndView.addObject("exerciseWeekMin",avgWeek()%60);
                modelAndView.addObject("exercise",exerciseHistory);
                break;
            case CAREGIVER:
                exerciseHistory = exerciseService.getExerciseAllRecord(getCaretaker().getUserId(), -1, -1);
                modelAndView.addObject("exerciseWeekHour",avgWeek()/60);
                modelAndView.addObject("exerciseWeekMin",avgWeek()%60);
                modelAndView.addObject("exercise",exerciseHistory);
                break;

        }
        modelAndView.addObject("type",getUser().getType());
        modelAndView.addObject("previousUrl","exercise");
        return modelAndView;
    }
    @PostMapping("/exercise")
    @ResponseBody
    public Result<Tbl_Exercise_record> PostPatientVisit(@RequestBody Tbl_Exercise_record tbl_exercise_record){
        String userId = getUsername();
        tbl_exercise_record.setPk(new RecordKey(userId, LocalDate.now()));
        tbl_exercise_record.setWeek(getUser().getWeek());
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
                .code(code)
                .data(seved)
                .build();
    }
    public Integer avgWeek(){
        Integer avg = 0;
        LocalDate now = LocalDate.now();
        String username = "";
        if(getUser().getType() == User.Type.PATIENT){
            username = getUsername();
        }else if(getUser().getType() == User.Type.CAREGIVER){
            username = getCaretaker().getUserId();
        }
        for(int i=1; i<8; i++){
            avg = avg + exerciseService.getExerciseDayRecord(new RecordKey(username,now.with(DayOfWeek.of(i))));
        }
        return avg/7;
    }
}
