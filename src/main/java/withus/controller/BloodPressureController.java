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
import withus.service.BloodPressureService;
import withus.service.CountService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;



@Controller
public class BloodPressureController extends BaseController {
    private final BloodPressureService bloodPressureService;
    private final CountService countService;

    @Autowired
    public BloodPressureController(AuthenticationFacade authenticationFacade, UserService userService, BloodPressureService bloodPressureService, CountService countService) {
        super(userService, authenticationFacade);
        this.bloodPressureService = bloodPressureService;
        this.countService = countService;
    }

    @GetMapping("/bloodPressure")
    public ModelAndView getBloodPressure() {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure");

        if (bloodPressureService.getTodayBloodRecord(new RecordKey(user.getUserId(), LocalDate.now())) == null) {
            modelAndView.addObject("contraction", "");
            modelAndView.addObject("pressure", "");
            modelAndView.addObject("relaxation", "");
            logger.info("id:{}, today bloodPressure:null", user.getUserId());
        } else {
            Tbl_blood_pressure_pulse today = bloodPressureService.getTodayBloodRecord(new RecordKey(user.getUserId(), LocalDate.now()));
            modelAndView.addObject("contraction", today.getContraction());
            modelAndView.addObject("pressure", today.getPressure());
            modelAndView.addObject("relaxation", today.getRelaxation());
            logger.info("id:{}, contraction:{}, pressure:{}, relaxation:{}", user.getUserId(), today.getContraction(), today.getPressure(), today.getRelaxation());
        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("patientCount", count);
            logger.info("Patient Count: {}", count);
        } else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            logger.info("Caregiver Count: {}", count);
            modelAndView.addObject("caregiverCount", count);

        }

        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }

    @GetMapping("/bloodPressure-all-history")
    public ModelAndView getBloodPressureRecord() {
        ModelAndView modelAndView = new ModelAndView("bloodPressure/bloodPressure-all-history");

        User user = getUser();
        logger.info("id:{}, type:{}", user.getUserId(), user.getType());
        modelAndView.addObject("user", user);

        List<Tbl_blood_pressure_pulse> bloodWeek = bloodPressureService.getALlBloodRecord(user.getUserId());

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("bloodWeek", bloodWeek);
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;
    }

    @PostMapping("/bloodPressure")
    @ResponseBody
    public Result<Tbl_blood_pressure_pulse> PostPatientVisit(@RequestBody Tbl_blood_pressure_pulse tbl_blood_pressure_pulse) {
        User user = getUser();
        tbl_blood_pressure_pulse.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_blood_pressure_pulse.setWeek(user.getWeek());
        Result.Code code;
        Tbl_blood_pressure_pulse saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = bloodPressureService.upsertBloodPressureRecord(tbl_blood_pressure_pulse);
                code = Result.Code.OK;
            } else if(user.getType() == User.Type.CAREGIVER && user.getWeek() != 25){
                saved = bloodPressureService.upsertBloodPressureRecord(tbl_blood_pressure_pulse);
                code = Result.Code.OK;
            } else if (user.getWeek() == 25) {
                throw new IllegalStateException("25 Weeks User try input data [warn]");
            } else {
                throw new IllegalStateException("Admin try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_blood_pressure_pulse>builder()
                .code(code)
                .data(saved)
                .build();
    }
}
