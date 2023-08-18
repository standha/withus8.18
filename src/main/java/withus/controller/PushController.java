package withus.controller;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import withus.auth.AuthenticationFacade;
import withus.entity.User;
import withus.scheduler.NoticeScheduler;
import withus.service.AndriodSingleNotification;
import withus.service.AndroidPushNotificationService;
import withus.service.UserService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class PushController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final AndroidPushNotificationService androidPushNotificationService;

    private final NoticeScheduler noticeScheduler;

    public PushController(UserService userService, AuthenticationFacade authenticationFacade, AndroidPushNotificationService androidPushNotificationService, NoticeScheduler noticeScheduler) {
        super(userService, authenticationFacade);
        this.androidPushNotificationService = androidPushNotificationService;
        this.noticeScheduler = noticeScheduler;
    }

    @GetMapping("/caregiver-push/{page}")
    public ResponseEntity<String> pushAlarm(@PathVariable("page") String page) throws InterruptedException{
        User user = getUser();
        if(user.getCaregiver() == null){
            return ResponseEntity.badRequest().body("Validation failed");
        }
        User caregiver = user.getCaregiver();

        if(caregiver.getAppToken() == null || caregiver.getName() == null){
            return ResponseEntity.badRequest().body("Validation failed");
        }
        String message = null;
        switch (page){
            case "medicine":
                message = caregiver.getName() + "님, 가족의 [복약]기록을 확인해보세요!";
                break;
            case "bloodPressure":
                message = caregiver.getName() + "님, 가족의 [혈압/맥박]기록을 확인해보세요!";
                break;
            case "symptom":
                message = caregiver.getName() +"님, 가족의 [증상일지]기록을 확인해보세요!";
                break;
            case "exercise":
                message = caregiver.getName() +"님, 가족의 [운동]기록을 확인해보세요!";
                break;
            case "natriumMoisture":
                message = caregiver.getName() + "님, 가족의 [저염식이]기록을 확인해보세요!";
                break;
            case "waterIntake":
                message = caregiver.getName() + "님, 가족의 [수분섭취]기록을 확인해보세요!";
                break;
            case "weight":
                message = caregiver.getName() + "님, 가족의 [체중]기록을 확인해보세요!";
                break;
            case "mindScore":
                message = caregiver.getName() + "님, 가족의 [마음 점수]기록을 확인해보세요!";
                break;
            default:
                return ResponseEntity.badRequest().body("Validation failed");
        }
        noticeScheduler.send(page,caregiver.getAppToken(),message);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/caregiver-request-push/{page}")
    public ResponseEntity<String> pushAlarmRequest(@PathVariable("page") String page) throws InterruptedException{
        User user = getUser();
        User patient = userService.getUserByCaregiverId(user.getUserId());

        if(patient == null || patient.getAppToken()== null || patient.getName() == null){
            return ResponseEntity.badRequest().body("Validation failed");
        }
        String message = null;
        switch (page){
            case "medicine":
                message = patient.getName() + "님 보호자님이 [복약]기록을 궁금해 해요.\n복약을 기록해 볼까요?";
                break;
            case "bloodPressure":
                message = patient.getName() + "님 보호자님이 [혈압/맥박]기록을 궁금해 해요.\n혈압/맥박을 기록해 볼까요?";
                break;
            case "symptom":
                message = patient.getName() +"님 보호자님이 증상일지 기록을 궁금해 해요.\n증상일지를 기록해 볼까요?";
                break;
            case "exercise":
                message = patient.getName() +"님 보호자님이 [운동]기록을 궁금해 해요.\n운동을 기록해 볼까요?";
                break;
            case "natriumMoisture":
                message = patient.getName() + "님 보호자님이 [저염식이]기록을 궁금해 해요.\n염분을 기록해 볼까요?";
                break;
            case "waterIntake":
                message = patient.getName() + "님 보호자님이 [수분섭취]기록을 궁금해 해요.\n수분을 기록해 볼까요?";
                break;
            case "weight":
                message = patient.getName() + "님 보호자님이 [체중]기록을 궁금해 해요.\n체중을 기록해 볼까요?";
                break;
            case "mindScore":
                message = patient.getName() + "님, 가족의 [마음 점수]기록을 확인해보세요!";
                break;
            default:
                return ResponseEntity.badRequest().body("Validation failed");
        }
        noticeScheduler.send(page,patient.getAppToken(),message);
        return ResponseEntity.ok("success");
    }

}
