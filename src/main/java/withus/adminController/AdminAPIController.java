package withus.adminController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.*;
import withus.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AdminAPIController extends withus.controller.BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final AdminService adminService;
    private final CountService countService;

    @Autowired
    public AdminAPIController(UserService userService, AuthenticationFacade authenticationFacade, AdminService adminService, CountService countService) {
        super(userService, authenticationFacade);
        this.adminService = adminService;
        this.countService = countService;
    }
    @GetMapping("/patient-main-button/{userId}")
    public ResponseEntity<Map<String, Object>> patientMainButton(@PathVariable("userId") String userId){
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_patient_main_button_count> counts = adminService.getPatientMainButtonCountAsc(userId);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient-sub-button/{userId}")
    public ResponseEntity<Map<String, Object>> patientSubButton(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_patient_sub_button_count> counts = adminService.getPatientSubButtonCountAsc(userId);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/patient-detail-button/{userId}")
    public ResponseEntity<Map<String, Object>> patientDetailButton(@PathVariable("userId") String userId){
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_patient_detail_button_count> counts = adminService.getPatientDetailButtonCountAsc(userId);
        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/caregiver-main-button/{userId}")
    public ResponseEntity<Map<String, Object>> caregiverMainButton(@PathVariable("userId") String userId){
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_caregiver_main_button_count> counts = adminService.getCaregiverMainButtonCountAsc(userId);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/caregiver-sub-button/{userId}")
    public ResponseEntity<Map<String, Object>> caregiverSubButton(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_caregiver_sub_button_count> counts = adminService.getCaregiverSubButtonCountAsc(userId);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/caregiver-detail-button/{userId}")
    public ResponseEntity<Map<String, Object>> caregiverDetailButton(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        List<Tbl_caregiver_detail_button_count> counts = adminService.getCaregiverDetailButtonCountAsc(userId);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("counts", counts);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value="/family_data/{userId}")
    public ResponseEntity<Map<String, Object>> familyData(@PathVariable("userId") String userId) {
        User user = getUser();
        Map<String, Object> response = new ConcurrentHashMap<>();
        User.Type type = user.getType();
        String familyId = null;

        if(type == User.Type.PATIENT){
            if(user.getCaregiver() != null){
                User existCaregiver = userService.getUserByContact(user.getCaregiver().getContact());
                if(existCaregiver != null){
                    familyId = existCaregiver.getUserId();
                }
            }
            response.put("familyId", familyId);
        } else if (type == User.Type.CAREGIVER) {
            User existPatient = userService.getUserById(getConnectId());
            if(getConnectId() != null && existPatient != null){
                familyId = existPatient.getUserId();
            }
            response.put("familyId", familyId);
        }
        return ResponseEntity.ok(response);
    }

}
