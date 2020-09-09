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
import withus.service.AlarmService;
import withus.service.AndroidPushNotificationService;
import withus.service.AndroidPushPeriodicNotifications;
import withus.service.UserService;

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

    //@Scheduled(cron = "0 * * * * *")
    public @ResponseBody ResponseEntity<String> pill() throws JSONException, InterruptedException  {
        System.out.println("복약 알림 Scheduler ");
        if(noticePill().isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String messageBody = " 약 먹을 시간이에요";
        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson("",messageBody,noticePill());
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




    public List<String> noticePill() throws NullPointerException{
        List<String> pillToken = new ArrayList<String>();
        List<Tbl_medication_alarm> alarms = alarmService.getPillAlarmOn();
        for (Tbl_medication_alarm alarm : alarms) {
            LocalTime localTime = LocalTime.now();

            if(alarm.getMedicationTimeMorning() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeMorning().getHour() && localTime.getMinute() == alarm.getMedicationTimeMorning().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    pillToken.add(idToken.getAppToken());
                }
            }

            if(alarm.getMedicationTimeLaunch() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeLaunch().getHour() && localTime.getMinute() == alarm.getMedicationTimeLaunch().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    pillToken.add(idToken.getAppToken());
                }
            }

            if(alarm.getMedicationTimeDinner() != null) {
                if (localTime.getHour() == alarm.getMedicationTimeDinner().getHour() && localTime.getMinute() == alarm.getMedicationTimeDinner().getMinute()) {
                    User idToken = userService.getUserById(alarm.getId());
                    pillToken.add(idToken.getAppToken());
                }
            }
        }
        return pillToken;
    }
    //cron = "0 0 20 * * *"
    //매일 20시 위더스 알림
    //@Scheduled(cron = "0 * * * * *")
    @RequestMapping(produces="application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> send() throws JSONException, InterruptedException, NonUniqueResultException {
        String messageBody = " 님, 오늘 심장 건강을 위해 실천하신 내용을 [위더스]에 기록하셨나요?\n 기록하지 않았다면 지금 기록해주세요!";
        System.out.println("매일 20시 위더스 알림");
        if(RunAt20AllToken().isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson("yang",messageBody,RunAt20AllToken());
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

    public List<String> RunAt20AllToken(){
        List<String>tokenList = new ArrayList<String>();
        List<User> alltokens = userService.getAllToken();
        for(User alltoken:alltokens){
            if(alltoken.getType().equals(User.Type.PATIENT)) {
                tokenList.add(alltoken.getAppToken());
            }
        }
        return tokenList;
    }

    //외래 진료 전날 18시 알림
    //@Scheduled(cron = "0 * * * * *")
    public @ResponseBody ResponseEntity<String> visit18() throws JSONException, InterruptedException  {
        System.out.println("예약 전일 오후 6시 알림");
        if(noticeTomorrowVisit().isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String messageBody = " 내일 병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!";
        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson("",messageBody,noticeTomorrowVisit());
        HttpEntity<String> requests = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationService.send(requests);
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

    public List<String> noticeTomorrowVisit() {
        List<String> visitToken = new ArrayList<String>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Tbl_outpatient_visit_alarm> visits = alarmService.getVisitAlarmOn();
            for (Tbl_outpatient_visit_alarm visit : visits) {
            if(tomorrow.equals(visit.getOutPatientVisitDate())){
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
        return visitToken;
    }

    //외래 진료 2시간전 알림
    //@Scheduled(cron = "0 * * * * *")
    @RequestMapping(produces="application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> visit2() throws JSONException, InterruptedException  {
        String messageBody = " 병원 진료 전 준비사항 (예: 금식)이 있는 지 확인해보세요!";
        System.out.println("진료 2시간 전 Scheduler ");
        if(noticeTwoVisit().isEmpty()){
            return new ResponseEntity<>("No Target!", HttpStatus.BAD_REQUEST);
        }

        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson("",messageBody,noticeTwoVisit());
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

    public List<String> noticeTwoVisit() {
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
        return visitToken;
    }
}
