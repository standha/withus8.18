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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@EnableAsync
@EnableScheduling
public class SeedScheduler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final GoalService goalService;
    private final SeedService seedService;

    @Autowired
    public SeedScheduler(UserService userService, GoalService goalService, SeedService seedService) {
        this.userService = userService;
        this.goalService = goalService;
        this.seedService = seedService;
    }

    @Async
    @Scheduled(cron = "0 0 0 * * 2-7")
    public void initDay() {
        logger.info("Scheduler:{}, ThreadName:{}", "CheckAchievement", Thread.currentThread().getName());

        List<User> allPatients = userService.getUserAllByType(User.Type.PATIENT);
        if (allPatients == null) {
            return;
        }
        for (User user : allPatients) {
            Tbl_goal goal = goalService.getGoalId(user.getUserId());
            if (user.getWeek() != 25 && user.getWeek() >= 1) {
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
                        .topGoal(goal.getTop_goals())
                        .middleGoal(goal.getMiddle_goals())
                        .bottomGoal(goal.getBottom_goals())
                        .build();
                seedService.upsertPatientSeed(patientSeedDay);

                Tbl_patient_seed_day seedDay = seedService.getPatientSeed(new RecordKey(user.getUserId(),LocalDate.now().minusDays(1)));
                if(seedDay != null){
                    seedDay.setTopGoal(goal.getTop_goals());
                    seedDay.setMiddleGoal(goal.getMiddle_goals());
                    seedDay.setBottomGoal(goal.getBottom_goals());
                    seedService.upsertPatientSeed(seedDay);
                }
            }
        }


        List<User> allCaregivers = userService.getUserAllByType(User.Type.CAREGIVER);
        if (allCaregivers == null) {
            return;
        }

        for (User user : allCaregivers) {
            Tbl_goal goal = goalService.getGoalId(user.getUserId());
            if (user.getType() == User.Type.CAREGIVER && user.getWeek() >= 1) {
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
                        .topGoal(goal.getTop_goals())
                        .middleGoal(goal.getMiddle_goals())
                        .bottomGoal(goal.getBottom_goals())
                        .build();
                seedService.upsertCaregiverSeed(caregiverSeedDay);
            }
        }

    }

    @Async
    @Scheduled(cron = "0 0 0 * * MON")
    public void weekSeedCheck() {
        List<User> allPatients = userService.getUserAllByType(User.Type.PATIENT);
        if (allPatients == null) {
            return;
        }
        for (User user : allPatients) {
            Tbl_goal goals = goalService.getGoalId(user.getUserId());
            if(goals != null) {
                ArrayList<String> goalList = new ArrayList<>();
                if(goals.getTop_goals() != null){
                    goalList.add(goals.getTop_goals());
                }
                if(goals.getMiddle_goals() != null){
                    goalList.add(goals.getMiddle_goals());
                }
                if(goals.getBottom_goals() != null){
                    goalList.add(goals.getBottom_goals());
                }
                for (String goal : goalList){
                    switch (goal){
                        case "주 1회 이상 정해진 시간에 약 복용":

                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}




