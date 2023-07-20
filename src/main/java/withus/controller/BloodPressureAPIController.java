package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import withus.auth.AuthenticationFacade;
import withus.entity.Tbl_blood_pressure_pulse;
import withus.entity.User;
import withus.service.BloodPressureService;
import withus.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BloodPressureAPIController extends BaseController {

    private final BloodPressureService bloodPressureService;

    @Autowired
    public BloodPressureAPIController(UserService userService, AuthenticationFacade authenticationFacade, BloodPressureService bloodPressureService) {
        super(userService, authenticationFacade);
        this.bloodPressureService = bloodPressureService;
    }

    @GetMapping("/blood-get-data")
    public ResponseEntity<Map<String, Object>> patientMainButton(){
        User user = getUser();
        List<Tbl_blood_pressure_pulse> bloodList = bloodPressureService.getALlBloodRecord(user.getUserId());

        Map<String, Object> response = new HashMap<>();
        response.put("bloodList", bloodList);

        return ResponseEntity.ok(response);
    }

}
