
package withus.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import withus.entity.*;
import withus.service.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@EnableAsync
@EnableScheduling
public class GoalScheduler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final MoistureNatriumService moistureNatriumService;
    private final BloodPressureService bloodPressureService;
    private final WeightService weightService;
    private final SymptomService symptomService;
    private final ExerciseService exerciseService;
    private final AlarmService alarmService;
    private final GoalService goalService;
    private final AndroidPushNotificationService androidPushNotificationService;

    @Autowired
    public GoalScheduler(UserService userService, MoistureNatriumService moistureNatriumService, BloodPressureService bloodPressureService,
                         WeightService weightService, SymptomService symptomService, ExerciseService exerciseService, AlarmService alarmService,
                         GoalService goalService, AndroidPushNotificationService androidPushNotificationService) {
        this.moistureNatriumService = moistureNatriumService;
        this.userService = userService;
        this.bloodPressureService = bloodPressureService;
        this.weightService = weightService;
        this.symptomService = symptomService;
        this.exerciseService = exerciseService;
        this.alarmService = alarmService;
        this.goalService = goalService;
        this.androidPushNotificationService = androidPushNotificationService;
    }

    public int NatriumCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (moistureNatriumService.getNatriumTodayRecord(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                count++;
            }
        }
        return count;
    }

    public int BloodCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (bloodPressureService.getTodayBloodRecord(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                count++;
            }
        }
        return count;
    }

    public int WeightCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (weightService.getTodayWeight(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                count++;
            }
        }
        return count;
    }

    public int SymptomCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (symptomService.getSymptom(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                count++;
            }
        }
        return count;
    }

    public int ExerciseCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (exerciseService.getExercise(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                Tbl_Exercise_record exercise = exerciseService.getExercise(new RecordKey(id, today.with(DayOfWeek.of(i))));
                int record = (exercise.getHour() * 60) + exercise.getMinute();
                if (record >= 30)
                    count++;
            }
        }
        return count;
    }

    public int PillCount(String id) {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) {
            if (alarmService.getMedicationRecord(new RecordKey(id, today.with(DayOfWeek.of(i)))) != null) {
                Tbl_medication_record record = alarmService.getMedicationRecord(new RecordKey(id, today.with(DayOfWeek.of(i))));
                if (record.isFinished() == true) {
                    count++;
                }
            }
        }
        return count;
    }

    //cron = "0 0 21 * * SUN"
    @Async
    @Scheduled(cron = "0 0 21 * * SUN")
    public void GoalList() {
        logger.info("Scheduler:{}, ThreadName:{}","CheckAchievement",Thread.currentThread().getName());
        List<User> users = userService.getPatientLimit(User.Type.PATIENT, 25, 24);
        List<String> noneToken = new ArrayList<>();
        List<String> loseToken = new ArrayList<>();
        List<String> winToken = new ArrayList<>();

        if (users == null) {
            return;
        }

        for (User user : users) {
            Tbl_goal goalUser = goalService.getGoalId(user.getUsername());
            User guser = user.getCaregiver();
            int success = 0;

            switch (goalUser.getGoal()) {
                case 0:
                    success = 2;
                    if (user.getAppToken() != null) {
                        noneToken.add(user.getAppToken());
                    }
                    if (user.getCaregiver() == null)
                        break;
                    else {
                        if (guser.getAppToken() != null) {
                            noneToken.add(guser.getAppToken());
                            logger.info("No goal set, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                        }
                    }
                    break;
                case 1:
                    if (PillCount(user.getUserId()) == 7) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 2:
                    if (BloodCount(user.getUserId()) == 7) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 3:
                    if (WeightCount(user.getUserId()) == 7) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 4:
                    if (SymptomCount(user.getUserId()) >= 3) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 5:
                    if (SymptomCount(user.getUserId()) == 7) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 6:
                    if (NatriumCount(user.getUserId()) >= 3) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 7:
                    if (NatriumCount(user.getUserId()) == 7) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 8:
                    if (ExerciseCount(user.getUserId()) >= 1) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;
                case 9:
                    if (ExerciseCount(user.getUserId()) >= 3) {
                        success = 1;
                        if (user.getAppToken() != null) {
                            winToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                winToken.add(guser.getAppToken());
                                logger.info("Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    } else {
                        if (user.getAppToken() != null) {
                            loseToken.add(user.getAppToken());
                        }
                        if (user.getCaregiver() == null)
                            break;
                        else {
                            if (guser.getAppToken() != null) {
                                loseToken.add(guser.getAppToken());
                                logger.info("Not Achieve the goal, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                            }
                        }
                    }
                    break;

                default:
                    break;
            }

            if (success == 1) {
                user.setLevel(user.getLevel() + 1);
                userService.upsertUser(user);
                logger.info("Achieve the goal, id:{}, type:{}, level:{}, week:{} goal:{}", user.getUserId(), user.getType(), user.getLevel(), user.getWeek(), goalUser.getGoal());
            } else if (success == 2) {
                logger.info("No goal set, id:{}, type:{}, level:{}, week:{} , goal:{}", user.getUserId(), user.getType(), user.getLevel(), user.getWeek(), goalUser.getGoal());
            } else {
                logger.info("Not Achieve the goal, id:{}, type:{}, level:{}, week:{} goal:{}", user.getUserId(), user.getType(), user.getLevel(), user.getWeek(), goalUser.getGoal());
            }

        }

        try {
            levelNotice("Achieve the goal", winToken, "이 주의 목표를 달성하셨네요!\n꽃이 어디까지 피었는지 확인해주세요~");
            levelNotice("No goal set", noneToken, "이 주의 목표 설정이 되어있지 않아요.\n목표를 설정하여 꽃을 피워보세요.");
            levelNotice("Not Achieve the goal", loseToken, "아쉽게도 이 주의 목표를 달성하지 못하셨네요.\n꽃이 어디까지 피었는지 확인해주세요.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public @ResponseBody
    ResponseEntity<String> levelNotice(String title, List<String> tokenList, String message) throws InterruptedException {
        if (tokenList.isEmpty()) {
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson("center", message, tokenList);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            logger.info("push:{}, result:{}", title, firebaseResponse);
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            logger.debug("got interrupted!");
            throw new InterruptedException();
        } catch (ExecutionException e) {
            logger.debug("execution error!");
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
