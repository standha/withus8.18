package withus.controller;

import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import withus.entity.CaregiverProgressKey;
import withus.entity.Tbl_caregiver_main_button_count;
import withus.entity.User;
import withus.service.CountService;
import withus.service.UserService;

public class BloodPressureContoller {
    private final CountService countService;
    private final UserService userService;

    public BloodPressureContoller(CountService countService, UserService userService) {
        this.countService = countService;
        this.userService = userService;
    }

    @Test
    public void test(){

        User user = null;
        user.setUserId("testCaregiver");
        user.setWeek(2);

        Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
        Assertions.assertThat(count.getAlarm()).isEqualTo(0);
    }
}
