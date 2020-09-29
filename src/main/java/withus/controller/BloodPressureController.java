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
import withus.entity.User;
import withus.service.BloodPressureService;
import withus.service.ExerciseService;
import withus.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure");
        if(bloodPressureService.getTodayBloodRecord(new RecordKey(getConnectId(),LocalDate.now())) == null){
            modelAndView.addObject("contraction","");
            modelAndView.addObject("pressure", "");
            modelAndView.addObject("relaxation", "");
            logger.info("id:{}, today bloodPressure:null", user.getUserId());
        }
        else{
            Tbl_blood_pressure_pulse today= bloodPressureService.getTodayBloodRecord(new RecordKey(getConnectId(),LocalDate.now()));
            modelAndView.addObject("contraction", today.getContraction());
            modelAndView.addObject("pressure", today.getPressure());
            modelAndView.addObject("relaxation", today.getRelaxation());
            logger.info("id:{}, contraction:{}, pressure:{}, relaxation:{}", user.getUserId(), today.getContraction(), today.getPressure(), today.getRelaxation());
        }
        modelAndView.addObject("type", getUser().getType());
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }

    @GetMapping("/bloodPressure-all-history")
    @Statistical
    public ModelAndView getBloodPressureRecord() {
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure-all-history");
        List<Tbl_blood_pressure_pulse> bloodPressureHistory;
        bloodPressureHistory = bloodPressureService.getBloodAllRecord(getConnectId(),-1,-1,-1);
        LocalDate today = LocalDate.now();

        List<Tbl_blood_pressure_pulse> bloodWeek = new ArrayList<>();
        for(int i=1; i<=7; i++)
        {
            if(bloodPressureService.getTodayBloodRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i))))!=null)
            {
                Tbl_blood_pressure_pulse week = bloodPressureService.getTodayBloodRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i))));
                bloodWeek.add(week);
            }
        }

        modelAndView.addObject("bloodWeek", bloodWeek);
        modelAndView.addObject("bloodPressure",bloodPressureHistory);
        modelAndView.addObject("previousUrl", "/center");
        return modelAndView;
    }

    @PostMapping("/bloodPressure")
    @Statistical
    @ResponseBody
    public Result<Tbl_blood_pressure_pulse> PostPatientVisit(@RequestBody Tbl_blood_pressure_pulse tbl_blood_pressure_pulse){
        String userId = getUsername();
        tbl_blood_pressure_pulse.setPk(new RecordKey(userId, LocalDate.now()));
        tbl_blood_pressure_pulse.setWeek(getUser().getWeek());
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
                .code(code)
                .data(seved)
                .build();
    }
}
