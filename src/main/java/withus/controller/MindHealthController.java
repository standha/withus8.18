package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.*;
import withus.service.CountService;
import withus.service.MindHealthService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MindHealthController extends BaseController {
    private final MindHealthService mindHealthService;
    private final CountService countService;

    @Autowired
    public MindHealthController(AuthenticationFacade authenticationFacade, UserService userService, MindHealthService mindHealthService, CountService countService) {
        super(userService, authenticationFacade);
        this.mindHealthService = mindHealthService;
        this.countService = countService;
    }

    @GetMapping("/mindHealth")
    public ModelAndView getMindHealth() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/mindHealth");
        modelAndView.addObject("previousURL", "/center");
        User user = getUser();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        modelAndView.addObject("type", user.getType());

        return modelAndView;
    }

    @GetMapping("/minddiary")
    public ModelAndView getMinddiary() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/minddiary");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
        }


        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;

    }

    @GetMapping("/minddiary1")
    public ModelAndView getMindDiary1() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/minddiary1");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }

    @GetMapping("/mindhealth-all-history")
    public ModelAndView getMindHealthAll() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/mindhealth-all-history");
        User user = getUser();
        List<Tbl_mindHealth_record> mindDiaryHistories = mindHealthService.getAllMindHealthRecord(getConnectId());

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());
            modelAndView.addObject("minddiary",mindDiaryHistories);

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/minddiary1");

        return modelAndView;


    }
    @GetMapping("/mindcontrol")
    public ModelAndView getMindControl() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/mindcontrol");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_caregiver_detail_button_count detailCount = countService.getCaregiverDetailCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }
    @GetMapping("/meditation")
    public ModelAndView getMeditation() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/meditation");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_caregiver_detail_button_count detailCount = countService.getCaregiverDetailCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }
    @GetMapping("/physicalActivity")
    public ModelAndView getphysicalActivity() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/physicalActivity");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_caregiver_detail_button_count detailCount = countService.getCaregiverDetailCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }

    @GetMapping("/breath")
    public ModelAndView getBreath() {
        ModelAndView modelAndView = new ModelAndView("mindHealth/breath");
        User user = getUser();

        if (mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("mood", null);
            modelAndView.addObject("text", null);
            modelAndView.addObject("score", null);

        } else {
            Tbl_mindHealth_record mood = mindHealthService.getmindHealth(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("mood", mood.getMood());
            modelAndView.addObject("text", mood.getText());

        }

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }
        if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            Tbl_caregiver_detail_button_count detailCount = countService.getCaregiverDetailCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }


        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "/mindHealth");

        return modelAndView;


    }

}


