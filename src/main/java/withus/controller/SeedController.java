package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.RecordKey;
import withus.entity.Tbl_caregiver_seed_day;
import withus.entity.Tbl_patient_seed_day;
import withus.entity.User;
import withus.service.SeedService;
import withus.service.UserService;

import java.time.LocalDate;

@RestController
public class SeedController extends BaseController{
    private final SeedService seedService;
    @Autowired
    public SeedController(UserService userService, AuthenticationFacade authenticationFacade, SeedService seedService) {
        super(userService, authenticationFacade);
        this.seedService = seedService;
    }
    @GetMapping("/seed/{data}")
    public ResponseEntity<String> SeedCheck(@PathVariable("data") String data){
        User user = getUser();

        if(user.getType() == User.Type.PATIENT){
            Tbl_patient_seed_day seedDay = seedService.getPatientSeed(new RecordKey(user.getUserId(), LocalDate.now()));
            if(seedDay ==null){
                return ResponseEntity.badRequest().body("Validation failed");
            }
            switch (data){
                case "medicine":
                    seedDay.setMedicine(1);
                    break;
                case "bloodPressure":
                    seedDay.setBloodPressure(1);
                    break;
                case "exercise":
                    seedDay.setExercise(1);
                    break;
                case "symptom":
                    seedDay.setSymptom(1);
                    break;
                case "natriumMoisture":
                    seedDay.setNatirumMoisture(1);
                    break;
                case "waterIntake":
                    seedDay.setWaterIntake(1);
                    break;
                case "weight":
                    seedDay.setWeight(1);
                    break;
                case "mindDiary":
                    seedDay.setMindDiary(1);
                    break;
                case "mindScore":
                    seedDay.setMindScore(1);
                default:
                    return ResponseEntity.badRequest().body("Validation failed");
            }
            seedService.upsertPatientSeed(seedDay);
        } else if(user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_seed_day seedDay = seedService.getCaregiverSeed(new RecordKey(user.getUserId(), LocalDate.now()));
            if(seedDay ==null){
                return ResponseEntity.badRequest().body("Validation failed");
            }
            switch (data){
                case "medicine":
                    seedDay.setMedicine(1);
                    break;
                case "bloodPressure":
                    seedDay.setBloodPressure(1);
                    break;
                case "exercise":
                    seedDay.setExercise(1);
                    break;
                case "dietManagement":
                    seedDay.setDietManagement(1);
                    break;
                case "weight":
                    seedDay.setWeight(1);
                    break;
                case "mindDiary":
                    seedDay.setMindDiary(1);
                    break;
                case "mindScore":
                    seedDay.setMindScore(1);
                default:
                    return ResponseEntity.badRequest().body("Validation failed");
            }
            seedService.upsertCaregiverSeed(seedDay);
        }
        return ResponseEntity.ok("success");
    }


}
