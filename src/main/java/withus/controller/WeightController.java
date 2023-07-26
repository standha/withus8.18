package withus.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.UserService;
import withus.service.WeightService;

@Controller
public class WeightController extends BaseController {
    private final WeightService weightService;
    private final CountService countService;

    @Autowired
    public WeightController(UserService userService, AuthenticationFacade authenticationFacade, WeightService weightService, CountService countService) {
        super(userService, authenticationFacade);
        this.weightService = weightService;
        this.countService = countService;
    }

    @GetMapping("/weight")
    public ModelAndView getWeight() {

        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("weight/weight");
        User.Type typeCheck = getUser().getType();
        if (weightService.getTodayWeight(new RecordKey(getConnectId(), LocalDate.now())) == null) {

            logger.info("id:{}, today weight:null", user.getUserId());

            modelAndView.addObject("weight", ""); //객체가 비어있어 타임리프에 null point 오류를 해결해주도록 한다. weight에 0kg을 뷰해줌
        } else {
            Tbl_weight weight = weightService.getTodayWeight(new RecordKey(getConnectId(), LocalDate.now()));

            logger.info("id:{}, today weight:{}", user.getUserId(), weight.getWeight());

            modelAndView.addObject("weight", weight.getWeight());
        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        } else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }

    @GetMapping("/weight-history")
    public ModelAndView getWeightHistory() {
        ModelAndView modelAndView = new ModelAndView("weight/weight-history");
        modelAndView.addObject("previousUrl", "/weight");
        User user = getUser();

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());
        List<Tbl_weight> weightRecord;
        weightRecord = weightService.getWeightRecord(getConnectId(), 0);
        modelAndView.addObject("weightRecord", weightRecord);
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("height", user.getHeight());
        return modelAndView;
    }

    @PostMapping(value = "/weight", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_weight> getWeight(@RequestBody Tbl_weight tbl_weight) {
        User user = getUser();
        tbl_weight.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_weight.setWeek(user.getWeek());
        Result.Code code;
        Tbl_weight saved = null;
        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = weightService.upsertWeightRecord(tbl_weight);
                code = Result.Code.OK;
            } else if (user.getWeek() == 25)
                throw new IllegalStateException("25 Weeks User try input data [warn]");

            else
                throw new IllegalStateException("Caregiver try input data [warn]");

        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);

            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_weight>builder()
                .code(code)
                .data(saved)
                .build();
    }
}
