package withus.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.GoalService;
import withus.service.UserService;

import java.util.List;

@Component
@EnableAsync
@EnableScheduling
public class ProgressScheduler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final GoalService goalService;

    @Autowired
    public ProgressScheduler(UserService userService , GoalService goalService) {
        this.goalService = goalService;
        this.userService = userService;
    }

    //cron = "0 0 0 * * MON"
    @Scheduled(cron = "0 0 0 * * MON")
    public void progressAutoIncrement(){
        List<User> allPatients = userService.getPatient(User.Type.PATIENT);
        for(User user : allPatients){
            user.setWeek(user.getWeek()+1);
            userService.upsertUser(user);
        }
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void resetLevel(){
        List<Tbl_goal> goals = goalService.getAllGoal();
        for(Tbl_goal goal : goals){
            goal.setGoal(0);
            goalService.upsertGoal(goal);
        }
    }
}
