package withus.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import withus.entity.*;
import withus.service.GoalService;
import withus.service.SeedService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@EnableScheduling
public class ProgressScheduler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final GoalService goalService;
    private final SeedService seedService;
    @Autowired
    public ProgressScheduler(UserService userService, GoalService goalService, SeedService seedService) {
        this.goalService = goalService;
        this.userService = userService;
        this.seedService = seedService;
    }

    //cron = "0 0 0 * * MON"
    @Async
    @Scheduled(cron = "0 0 0 * * MON")
    public void weekAutoIncrement() {
        logger.info("Scheduler:{}, ThreadName:{}","WeekIncrement",Thread.currentThread().getName());
        List<User> allPatients = userService.getUserAllByType(User.Type.PATIENT);
        if(allPatients == null){
            return;
        }
        for (User user : allPatients) {
            if (user != null && user.getWeek() != null && user.getWeek() <= 24) {
                logger.info("ASIS id:{}, type:{}, week:{}", user.getUserId(), user.getType(), user.getWeek());

                user.setWeek(user.getWeek() + 1);
                userService.upsertUser(user);

                logger.info("TOBE id:{}, type:{}, week:{}", user.getUserId(), user.getType(), user.getWeek());


                Tbl_goal goal = goalService.getGoalId(user.getUserId());
                if(user.getWeek() == 24){
                    Tbl_patient_seed_day seedDay = seedService.getPatientSeed(new RecordKey(user.getUserId(),LocalDate.now().minusDays(1)));
                    if(seedDay != null && goal != null){
                        seedDay.setTopGoal(goal.getTop_goals());
                        seedDay.setMiddleGoal(goal.getMiddle_goals());
                        seedDay.setBottomGoal(goal.getBottom_goals());
                        seedService.upsertPatientSeed(seedDay);
                    }
                }
                if(goal != null){
                    goal.setWeek(user.getWeek());
                    goalService.upsertGoal(goal);
                }

            }
            if (user != null && user.getWeek() != null && user.getWeek() <= 24 && user.getWeek() > 0) {
                Tbl_patient_seed_day patientSeedDay = Tbl_patient_seed_day.builder()
                        .pk(new RecordKey(user.getUserId(), LocalDate.now()))
                        .week(user.getWeek())
                        .medicine(false)
                        .exercise(false)
                        .bloodPressure(false)
                        .natirumMoisture(false)
                        .waterIntake(false)
                        .symptom(false)
                        .weight(false)
                        .mindDiary(false)
                        .mindScore(false)
                        .topGoal(null)
                        .middleGoal(null)
                        .bottomGoal(null)
                        .build();
                seedService.upsertPatientSeed(patientSeedDay);
            }
        }

        List<User> allCaregivers = userService.getUserAllByType(User.Type.CAREGIVER);
        if(allCaregivers == null){
            return;
        }
        for (User user : allCaregivers) {
            if(user != null && user.getWeek() != null && user.getWeek() <= 24) {
                logger.info("ASIS id:{}, type:{}, week:{}", user.getUserId(), user.getType(), user.getWeek());

                user.setWeek(user.getWeek() + 1);
                userService.upsertUser(user);

                logger.info("TOBE id:{}, type:{}, week:{}", user.getUserId(), user.getType(), user.getWeek());

                Tbl_goal goal = goalService.getGoalId(user.getUserId());
                if(user.getWeek() == 24){
                    Tbl_patient_seed_day seedDay = seedService.getPatientSeed(new RecordKey(user.getUserId(),LocalDate.now().minusDays(1)));
                    if(seedDay != null && goal != null){
                        seedDay.setTopGoal(goal.getTop_goals());
                        seedDay.setMiddleGoal(goal.getMiddle_goals());
                        seedDay.setBottomGoal(goal.getBottom_goals());
                        seedService.upsertPatientSeed(seedDay);
                    }
                }
                if(goal != null){
                    goal.setWeek(user.getWeek());
                    goalService.upsertGoal(goal);
                }

            }

            if(user != null && user.getWeek() != null && user.getWeek() <=24 && user.getWeek() >0) {
                Tbl_caregiver_seed_day caregiverSeedDay = Tbl_caregiver_seed_day.builder()
                        .pk(new RecordKey(user.getUserId(), LocalDate.now()))
                        .week(user.getWeek())
                        .medicine(false)
                        .bloodPressure(false)
                        .exercise(false)
                        .dietManagement(false)
                        .weight(false)
                        .mindDiary(false)
                        .mindScore(false)
                        .topGoal(null)
                        .middleGoal(null)
                        .bottomGoal(null)
                        .build();
                seedService.upsertCaregiverSeed(caregiverSeedDay);
            }
        }
    }
    @Async
    @Scheduled(cron = "0 0 0 * * MON")
    public void resetLevel() {
        logger.info("Scheduler:{}, ThreadName:{}","ResetLevel",Thread.currentThread().getName());
        List<Tbl_goal> goals = goalService.getAllGoal();

        for (Tbl_goal goal : goals) {
            logger.info("ASIS id:{}, goal:{}", goal.getGoalId(), goal.getGoal());
            goal.setGoal(null);
            goalService.upsertGoal(goal);
            logger.info("TOBE id:{}, goal:{}", goal.getGoalId(), goal.getGoal());
        }
    }
}
