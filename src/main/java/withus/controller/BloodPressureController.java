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
import withus.entity.Tbl_blood_pressure_pulse;
import withus.service.BloodPressureService;
import withus.service.ExerciseService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BloodPressureController extends BaseController {
    private final BloodPressureService bloodPressureService;

    @Autowired
    public BloodPressureController(AuthenticationFacade authenticationFacade, UserService userService, BloodPressureService bloodPressureService){
        super(userService, authenticationFacade);
        this.bloodPressureService = bloodPressureService;
    }
    @GetMapping("/bloodPressure")
    @Statistical
    public ModelAndView getBloodPressure() {
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure");
        String user = getUsername();
        modelAndView.addObject("previousUrl", "/home");
        System.out.println("UserName : "+user);
        return modelAndView;
    }

    @GetMapping("/bloodPressure-all-history")
    @Statistical
    public ModelAndView getBloodPressureRecord() {
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure-all-history");
        String username = getUsername();
        List<Tbl_blood_pressure_pulse> bloodPressureHistory;
        bloodPressureHistory = bloodPressureService.getBloodAllRecord(username,-1,-1,-1);
        modelAndView.addObject("bloodPressure",bloodPressureHistory);
        modelAndView.addObject("previousUrl", "/home");
        return modelAndView;
    }

    @PostMapping("/bloodPressure")
    @Statistical
    @ResponseBody
    public Result<Tbl_blood_pressure_pulse> PostPatientVisit(@RequestBody Tbl_blood_pressure_pulse tbl_blood_pressure_pulse){
        String userId = getUsername();
        tbl_blood_pressure_pulse.setPk(new RecordKey(userId, LocalDate.now()));
        Result.Code code;
        Tbl_blood_pressure_pulse seved = null;
        try{
            seved = bloodPressureService.upsertBloodPressureRecord(tbl_blood_pressure_pulse);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_blood_pressure_pulse>builder()
                .setCode(code)
                .setData(seved)
                .createResult();
    }
}
