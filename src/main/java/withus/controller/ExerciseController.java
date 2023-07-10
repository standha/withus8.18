package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.ExerciseService;
import withus.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ExerciseController extends BaseController {
    private final ExerciseService exerciseService;
    private final CountService countService;

    @Autowired
    public ExerciseController(AuthenticationFacade authenticationFacade, UserService userService, ExerciseService exerciseService, CountService countService) {
        super(userService, authenticationFacade);
        this.exerciseService = exerciseService;
        this.countService = countService;
    }

    @GetMapping("/exercise")
    public ModelAndView getExercise() {
        ModelAndView modelAndView = new ModelAndView("exercise/exercise");
        User user = getUser();
        User.Type typeCheck = user.getType();

        switch (typeCheck) {
            case PATIENT:
                Tbl_patient_main_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.addObject("count", count);
                if (exerciseService.getExercise(new RecordKey(user.getUserId(), LocalDate.now())) == null) {
                    modelAndView.addObject("hour", "");
                    modelAndView.addObject("minute", "");

                    logger.info("id:{}, today exercise:null", user.getUserId());
                } else {
                    Tbl_Exercise_record exercise = exerciseService.getExercise(new RecordKey(user.getUserId(), LocalDate.now()));
                    modelAndView.addObject("hour", exercise.getHour());
                    modelAndView.addObject("minute", exercise.getMinute());

                    logger.info("id:{}, exerciseHour:{}, exerciseMinute:{}", user.getUserId(), exercise.getHour(), exercise.getMinute());
                }

                break;

            case CAREGIVER:
                if (exerciseService.getExercise(new RecordKey(getCaretaker().getUserId(), LocalDate.now())) == null) {
                    modelAndView.addObject("hour", "");
                    modelAndView.addObject("minute", "");

                    logger.info("id:{}, today exercise:null", user.getUserId());
                } else {
                    Tbl_Exercise_record exercise = exerciseService.getExercise(new RecordKey(getCaretaker().getUserId(), LocalDate.now()));
                    modelAndView.addObject("hour", exercise.getHour());
                    modelAndView.addObject("minute", exercise.getMinute());

                    logger.info("id:{}", user.getUserId());
                }

                break;
        }
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }

    @GetMapping("/exercise-all-history")
    public ModelAndView getExerciseAll() {
        ModelAndView modelAndView = new ModelAndView("exercise/exercise-all-history");
        User user = getUser();
        String username = getUsername();
        List<Tbl_Exercise_record> exerciseHistory;

        switch (user.getType()) {
            case PATIENT:
                Tbl_patient_main_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
                modelAndView.addObject("count", count);
                exerciseHistory = exerciseService.getExerciseAllRecord(user.getUserId(), -1, -1);
                modelAndView.addObject("exerciseWeekHour", avgWeek() / 60);
                modelAndView.addObject("exerciseWeekMin", avgWeek() % 60);
                modelAndView.addObject("exercise", exerciseHistory);

                break;

            case CAREGIVER:
                exerciseHistory = exerciseService.getExerciseAllRecord(getCaretaker().getUserId(), -1, -1);
                modelAndView.addObject("exerciseWeekHour", avgWeek() / 60);
                modelAndView.addObject("exerciseWeekMin", avgWeek() % 60);
                modelAndView.addObject("exercise", exerciseHistory);

                break;
        }

        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "exercise");
        return modelAndView;
    }

    @PostMapping("/exercise")
    @ResponseBody
    public Result<Tbl_Exercise_record> PostPatientVisit(@RequestBody Tbl_Exercise_record tbl_exercise_record) {
        User user = getUser();
        tbl_exercise_record.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_exercise_record.setWeek(user.getWeek());
        Result.Code code;
        Tbl_Exercise_record saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = exerciseService.upsertExerciseRecord(tbl_exercise_record);
                code = Result.Code.OK;
            } else if (user.getWeek() == 25) {
                throw new IllegalStateException("25 Weeks User try input data [warn]");
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_Exercise_record>builder()
                .code(code)
                .data(saved)
                .build();
    }

    public Integer avgWeek() {
        Integer avg = 0;
        LocalDate now = LocalDate.now();

        for (int i = 1; i < 8; i++) {
            avg = avg + exerciseService.getExerciseDayRecord(new RecordKey(getConnectId(), now.with(DayOfWeek.of(i))));
        }

        return avg;
    }
}
