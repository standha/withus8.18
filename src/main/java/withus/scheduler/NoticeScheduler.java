package withus.scheduler;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
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

    public @ResponseBody ResponseEntity<String> notice(String title , List<String>tokenList,String message) throws JSONException, InterruptedException  {
        if(tokenList.isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }
        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson(title,message,tokenList);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            logger.debug("got interrupted!");
            throw new InterruptedException();
        }
        catch (ExecutionException e){
            logger.debug("execution error!");
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

    //복약 PUSH 알림
    @Scheduled(cron = "0 * * * * *")
    public void pillNotice(){
        List<String>morningToken = new ArrayList<>();
        List<String>lunchToken = new ArrayList<>();
        List<String>dinnerToken = new ArrayList<>();
        List<Tbl_medication_alarm> alarms = alarmService.getPillAlarmOn();
        for (Tbl_medication_alarm alarm : alarms) {
            LocalTime localTime = LocalTime.now();

            if(alarm.getMedicationTimeMorning() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeMorning().getHour() && localTime.getMinute() == alarm.getMedicationTimeMorning().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    morningToken.add(idToken.getAppToken());
                }
            }

            if(alarm.getMedicationTimeLunch() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeLunch().getHour() && localTime.getMinute() == alarm.getMedicationTimeLunch().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    lunchToken.add(idToken.getAppToken());
                }
            }

            if(alarm.getMedicationTimeDinner() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeDinner().getHour() && localTime.getMinute() == alarm.getMedicationTimeDinner().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    dinnerToken.add(idToken.getAppToken());
                }
            }
        }
        try {
            notice("pill", morningToken,"아침 약을 복용하실 시간이에요.\n 지금 약을 복용해주세요!");
            notice("pill", lunchToken,"점심 약을 복용하실 시간이에요.\n 지금 약을 복용해주세요!");
            notice("pill", dinnerToken,"저녁 약을 복용하실 시간이에요.\n 약 복용 후 복약 기록에 기록해주세요.");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //외래 진료 전날 PUSH 알림
    @Scheduled(cron = "0 0 18 * * *")
    public void visitNotice(){
        List<String> visitToken = new ArrayList<String>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Tbl_outpatient_visit_alarm> visits = alarmService.getVisitAlarmOn();
        for (Tbl_outpatient_visit_alarm visit : visits) {
            if (tomorrow.equals(visit.getOutPatientVisitDate())) {
                User user = userService.getUserById(visit.getId());
                visitToken.add(user.getAppToken());
                User guser = user.getCaregiver();

                if (guser == null) {
                    break;
                } else {
                    visitToken.add(guser.getAppToken());
                }
            }
        }
        try {
            notice("visit", visitToken,"내일 병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //외래 진료 2시간전 PUSH알림
    @Scheduled(cron = "0 * * * * *")
    public void visitNotice2(){
        List<String> visitToken = new ArrayList<String>();
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();
        List<Tbl_outpatient_visit_alarm> visits = alarmService.getVisitAlarmOn();
        for (Tbl_outpatient_visit_alarm visit : visits) {
            if(date.isEqual(visit.getOutPatientVisitDate())&&time.getHour()==visit.getOutPatientVisitTime().getHour()&&time.getMinute()==visit.getOutPatientVisitTime().getMinute()){
                User user = userService.getUserById(visit.getId());
                visitToken.add(user.getAppToken());
                User guser = user.getCaregiver();
                if(guser==null){
                    break;
                }
                else{
                    visitToken.add(guser.getAppToken());
                }
            }
        }
        try {
            notice("visit", visitToken,"병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //위더스랑 1~8주차 PUSH알림
    @Scheduled(cron = "0 0 8 * * MON,TUE,THU,SAT")
    public void withusNotice1(){
        List<String>tokenList = new ArrayList<String>();
        List<User> patients = userService.getPatientToken(User.Type.PATIENT);
        for(User patient : patients){
            if(patient.getWeek() > 0 && patient.getWeek() < 9){
                tokenList.add(patient.getAppToken());
            }
        }
        try {
            notice("withus", tokenList," 방금 [ 위더스랑 ]에 메시지가 도착했어요. ");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //위더스랑 9~24주차 PUSH 알림
    @Scheduled(cron = "0 0 8 * * MON,WED,SAT")
    public void withusNotice2(){
        List<String>tokenList = new ArrayList<String>();
        List<User> patients = userService.getPatientToken(User.Type.PATIENT);
        for(User patient : patients){
            if(patient.getWeek() >= 9 && patient.getWeek() <= 24){
                tokenList.add(patient.getAppToken());
            }
        }
        try {
            notice("withus", tokenList," 방금 [ 위더스랑 ]에 메시지가 도착했어요. ");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //단일 PUSH cron = "0 0 20 * * *"
    //매일 20시 위더스랑 진도체크 알림
    @Scheduled(cron = "0 0 20 * * *")
    public void noticeRecordAt20(){
        List<User> patients = userService.getAllToken();
        for(User patient:patients){
            if(patient.getType().equals(User.Type.PATIENT)) {
                try {
                    send("pill",patient.getAppToken(),
                            patient.getName()+"님, 오늘 심장 건강을 위해 실천하신 내용을 [위더스]에 기록하셨나요?\n 기록하지 않았다면 지금 기록해주세요!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //단일 기기 PUSH 알림
    @RequestMapping(produces="application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> send(String title, String token,String message) throws JSONException, InterruptedException, NonUniqueResultException {
        if(token.isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String notifications = AndriodSingleNotification.SingleNotificationJson(title,message,token);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            logger.debug("got interrupted!");
            throw new InterruptedException();
        }
        catch (ExecutionException e){
            logger.debug("execution error!");
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}