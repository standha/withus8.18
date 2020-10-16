package withus.scheduler;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;
import withus.entity.User;
import withus.service.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@EnableAsync
@EnableScheduling
public class NoticeScheduler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlarmService alarmService;
    private final AndroidPushNotificationService androidPushNotificationService;
    private final UserService userService;

    @Autowired
    public NoticeScheduler(AlarmService alarmService, AndroidPushNotificationService androidPushNotificationService, UserService userService) {
        this.alarmService = alarmService;
        this.androidPushNotificationService = androidPushNotificationService;
        this.userService = userService;
    }

    public @ResponseBody
    ResponseEntity<String> notice(String subject, List<String> tokenList, String message) throws InterruptedException {
        if (tokenList.isEmpty()) {
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String title;

        if (subject.startsWith("pill")) {
            title = "pill";
        } else if (subject.startsWith("withus")) {
            title = "withus";
        } else if (subject.equals("2 hours before visit") || subject.equals("1 day before visit")) {
            title = "visit";
        } else {
            title = "center";
        }

        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson(title, message, tokenList);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            logger.info("push:{}, result:{}", subject, firebaseResponse);

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            logger.info("got interrupted!");

            throw new InterruptedException();
        } catch (ExecutionException e) {
            logger.info("execution error!");
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

    //복약 PUSH 알림
    @Scheduled(cron = "0 * * * * *")
    public void pillNotice() {
        List<String> morningToken = new ArrayList<>();
        List<String> lunchToken = new ArrayList<>();
        List<String> dinnerToken = new ArrayList<>();
        List<Tbl_medication_alarm> alarms = alarmService.getPillAlarmOn();

        for (Tbl_medication_alarm alarm : alarms) {
            LocalTime localTime = LocalTime.now();

            if (alarm.getMedicationTimeMorning() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeMorning().getHour() && localTime.getMinute() == alarm.getMedicationTimeMorning().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    if (idToken.getAppToken() != null) {
                        morningToken.add(idToken.getAppToken());
                        logger.info("Taking morning pills, id:{}", idToken.getUserId());
                    }
                }
            }

            if (alarm.getMedicationTimeLunch() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeLunch().getHour() && localTime.getMinute() == alarm.getMedicationTimeLunch().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    if (idToken.getAppToken() != null) {
                        lunchToken.add(idToken.getAppToken());
                        logger.info("Taking lunch pills, id:{}", idToken.getUserId());
                    }
                }
            }

            if (alarm.getMedicationTimeDinner() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeDinner().getHour() && localTime.getMinute() == alarm.getMedicationTimeDinner().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    if (idToken.getAppToken() != null) {
                        dinnerToken.add(idToken.getAppToken());
                        logger.info("Taking dinner pills, id:{}", idToken.getUserId());
                    }
                }
            }
        }

        try {
            notice("pill_morning", morningToken, "아침 약을 복용하실 시간이에요.\n지금 약을 복용해주세요!");
            notice("pill_lunch", lunchToken, "점심 약을 복용하실 시간이에요.\n지금 약을 복용해주세요!");
            notice("pill_dinner", dinnerToken, "저녁 약을 복용하실 시간이에요.\n약 복용 후 복약 기록에 기록해주세요.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //외래 진료 전날 PUSH 알림cron = "0 0 18 * * *"
    @Scheduled(cron = "0 0 18 * * *")
    public void visitNotice() {
        List<String> visitToken = new ArrayList<String>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Tbl_outpatient_visit_alarm> visits = alarmService.getVisitAlarmOn();

        logger.info("tomorrow:{}", tomorrow);

        for (Tbl_outpatient_visit_alarm visit : visits) {
            if (tomorrow.equals(visit.getOutPatientVisitDate())) {
                User user = userService.getUserById(visit.getId());
                if (user.getAppToken() != null) {
                    visitToken.add(user.getAppToken());
                    logger.info("One day before the outpatient treatment, id:{}, type:{}", user.getUserId(), user.getType());
                }
                User guser = user.getCaregiver();
                if (guser != null) {
                    if (guser.getAppToken() != null) {
                        visitToken.add(guser.getAppToken());
                        logger.info("One day before the outpatient treatment, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                    }
                }
            }
        }
        try {
            notice("1 day before visit", visitToken, "내일 병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //외래 진료 2시간전 PUSH알림
    @Scheduled(cron = "0 * * * * *")
    public void visitNotice2() {
        List<String> visitToken = new ArrayList<String>();
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime().plusHours(2);
        List<Tbl_outpatient_visit_alarm> visits = alarmService.getVisitAlarmOn();

        logger.info("2 hours before outpatient time check, time:{}, alarm_on_count:{}", time, visits.stream().count());

        for (Tbl_outpatient_visit_alarm visit : visits) {
            if (visit.getOutPatientVisitTime() == null) {
                logger.info("outPatientVisitTime is null id:{}", visit.getId());

                continue;
            }

            if (date.isEqual(visit.getOutPatientVisitDate()) &&
                    time.getHour() == visit.getOutPatientVisitTime().getHour() &&
                    time.getMinute() == visit.getOutPatientVisitTime().getMinute()) {

                User user = userService.getUserById(visit.getId());
                if (user.getAppToken() != null) {
                    visitToken.add(user.getAppToken());

                    logger.info("2 hours before the outpatient treatment, id:{}, type:{}, token:{}", user.getUserId(), user.getType(), user.getAppToken());
                }

                User guser = user.getCaregiver();
                if (guser != null) {
                    if (guser.getAppToken() != null) {
                        visitToken.add(guser.getAppToken());

                        logger.info("2 hours before the outpatient treatment, id:{}, type:{}, patientId:{}", guser.getUserId(), guser.getType(), user.getUserId());
                    }
                }
            }
        }

        try {
            notice("2 hours before visit", visitToken, "병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //위더스랑 1~8주차 PUSH알림 cron = "0 0 8 * * MON,TUE,THU,SAT"
    @Scheduled(cron = "0 0 8 * * MON,TUE,THU,SAT")
    public void withusNotice1() {
        List<String> tokenList = new ArrayList<String>();
        List<User> patients = userService.getPatientToken(User.Type.PATIENT);

        for (User patient : patients) {
            if (patient.getWeek() > 0 && patient.getWeek() < 9) {
                if (patient.getAppToken() != null) {
                    tokenList.add(patient.getAppToken());
                    logger.info("1~8 Week WithusRang, id:{}", patient.getUserId());
                }
            }
        }

        try {
            notice("withus 1~8", tokenList, " 방금 [ 위더스랑 ]에 메시지가 도착했어요. ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //위더스랑 9~24주차 PUSH 알림
    @Scheduled(cron = "0 0 8 * * MON,WED,SAT")
    public void withusNotice2() {
        List<String> tokenList = new ArrayList<String>();
        List<User> patients = userService.getPatientToken(User.Type.PATIENT);

        for (User patient : patients) {
            if (patient.getWeek() >= 9 && patient.getWeek() <= 24) {
                if (patient.getAppToken() != null) {
                    tokenList.add(patient.getAppToken());
                    logger.info("9~24 Week WithusRang, id:{}", patient.getUserId());
                }
            }
        }

        try {
            notice("withus 9~24", tokenList, " 방금 [ 위더스랑 ]에 메시지가 도착했어요. ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //단일 PUSH cron = "0 0 20 * * *"
    //매일 20시 위더스랑 진도체크 알림
    @Scheduled(cron = "0 0 20 * * *")
    public void noticeRecordAt20() {
        List<User> patients = userService.getAllToken();
        for (User patient : patients) {
            if (patient.getType().equals(User.Type.PATIENT)) {
                if (patient.getWeek() <= 24) {
                    logger.info("Daily 20:00 record, id:{}, name:{}", patient.getUserId(), patient.getName());
                    try {
                        if (patient.getAppToken() != null) {
                            send("center", patient.getAppToken(),
                                    patient.getName() + "님, 오늘 심장 건강을 위해 실천하신 내용을 [위더스]에 기록하셨나요?\n기록하지 않았다면 지금 기록해주세요!");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //단일 기기 PUSH 알림
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody
    ResponseEntity<String> send(String title, String token, String message) throws InterruptedException, NonUniqueResultException {
        if (token.isEmpty()) {
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String notifications = AndriodSingleNotification.SingleNotificationJson(title, message, token);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            logger.info("push:{}, result:{}", "daily check", firebaseResponse);
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