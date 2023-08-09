package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import withus.auth.AuthenticationFacade;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.GoalService;
import withus.service.UserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class GoalAPIController extends BaseController {

    private final GoalService goalService;
@Autowired
    public GoalAPIController(UserService userService, AuthenticationFacade authenticationFacade, GoalService goalService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
    }

    @GetMapping("/goalList")
    public ResponseEntity<Map<String, Object>> patientMainButton(){
        User user = getUser();
        Tbl_goal goal = goalService.getGoalId(user.getUserId());

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("goalList", goal);

        return ResponseEntity.ok(response);
    }

}
