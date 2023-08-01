package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import withus.auth.AuthenticationFacade;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_duration_time;
import withus.entity.User;
import withus.service.DurationTimeService;
import withus.service.UserService;

import java.util.Objects;

@Controller
public class DurationTimeController extends BaseController{
    private final DurationTimeService durationTimeService;

    @Autowired
    public DurationTimeController(UserService userService, AuthenticationFacade authenticationFacade, DurationTimeService durationTimeService) {
        super(userService, authenticationFacade);
        this.durationTimeService = durationTimeService;
    }

    @PostMapping("/duration_time/{page}")
    public void AddDurationTime(@RequestBody int count, @PathVariable("page") PageType pageType) {
        User user = getUser();

        try {
            if (user.getType() == User.Type.PATIENT) {
                Tbl_patient_duration_time dt = durationTimeService.getPatientDurationTime(new ProgressKey(user.getUserId(), user.getWeek()));
                if (dt != null) {
                    pageType.processDurationTime(dt, count);
                }
            } else if (user.getType() == User.Type.CAREGIVER) {

            }
        } catch (Exception e) {

        }
    }

    public enum PageType {
        MAIN {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setMain(dt.getMain() + count);
            }
        },
        GOAL {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setGoal(dt.getGoal() + count);
            }
        },
        LEVEL {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setLevel(dt.getLevel() + count);
            }
        }
        ;

        public abstract void processDurationTime(Tbl_patient_duration_time dt, int count);
    }
}
