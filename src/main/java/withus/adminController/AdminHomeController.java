package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.*;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.entity.*;
import withus.service.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminHomeController extends withus.controller.BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final GoalService goalService;
    private final MoistureNatriumService moistureNatriumService;
    private final AdminService adminService;
    private final CountService countService;

    @Autowired
    public AdminHomeController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, AdminService adminService,
                               MoistureNatriumService moistureNatriumService, CountService countService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
        this.moistureNatriumService = moistureNatriumService;
        this.adminService = adminService;
        this.countService = countService;
    }

    @GetMapping("/user/{userId}")
    public ModelAndView viewPatient(@PathVariable("userId") String userId) {
        // @pathVariable, @ParameterValue, @Header
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        mav.addObject("patient", headerInfo);
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();

        mav.setViewName("Admin/admin_center");
        logger.info("user try to access admin_center id:{}, PatientId:{})", user.getUserId(),
                userId);
        return mav;
    }

    @GetMapping("/admin_moistureRecord/{userId}")
    public ModelAndView adminMoistureRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<MoistureAvgDTO> moistureAvg = adminService.getMoistureAvg(userId) == null ? null : adminService.getMoistureAvg(userId);
        List<Tbl_mositrue_record> moistureAsc = adminService.getMoistureAsc(userId) == null ? null : adminService.getMoistureAsc(userId);
        mav.addObject("weekAsc", moistureAsc);
        mav.addObject("weekAvg", moistureAvg);
        mav.addObject("patient", headerInfo);
        mav.setViewName("Admin/admin_moistureRecord");

        logger.info("user try to access admin_moistureRecord id:{}, PatientId:{})", user.getUserId(),
                userId);

        return mav;
    }

    @GetMapping("/admin_symptomRecord/{userId}")
    public ModelAndView adminSymptomRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView modelAndView = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<Tbl_symptom_log> symptom = adminService.getSymptom(userId) == null ? null : adminService.getSymptom(userId);
        List<SymptomAvgDTO> symptomAvg = adminService.getSymptomAvg(userId) == null ? null : adminService.getSymptomAvg(userId);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.addObject("symptom", symptom);
        modelAndView.addObject("symptomAvg", symptomAvg);
        modelAndView.setViewName("Admin/admin_symptomRecord");

        logger.info("user try to access admin_symptomRecord id:{}, PatientId:{})", user.getUserId(),
                userId);

        return modelAndView;
    }

    @GetMapping("/admin_exerciseRecord/{userId}")
    public ModelAndView adminExerciseRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView modelAndView = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<Tbl_Exercise_record> exercise = adminService.getExercise(userId);
        List<ExerciseDTO> exerciseAvg = adminService.getExerciseAvg(userId);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.addObject("exercise", exercise);
        modelAndView.addObject("exerciseAvg", exerciseAvg);
        modelAndView.setViewName("Admin/admin_exerciseRecord");

        logger.info("user try to access admin_exerciseRecord id:{}, PatientId:{})", user.getUserId(),
                userId);

        return modelAndView;
    }

    @GetMapping("/admin_pillRecord/{userId}")
    public ModelAndView adminPillRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<PillSumDTO> pillSum = adminService.getPillSum(userId);
        List<Tbl_medication_record> pillAsc = adminService.getPillAsc(userId);
        mav.addObject("pillSum", pillSum);
        mav.addObject("patient", headerInfo);
        mav.addObject("pillAsc", pillAsc);
        mav.setViewName("Admin/admin_pillRecord");

        logger.info("user try to access admin_pillRecord id:{}, PatientId:{})", user.getUserId(),
                userId);

        return mav;
    }

    @GetMapping("/admin_withusHelpRequest")
    public ModelAndView adminWithusHelpRequest() {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();
        mav.addObject("helpRequestAsc", helpRequestAsc);
        mav.setViewName("Admin/admin_withusHelpRequest");

        logger.info("user try to access admin_withusHelpRequest id:{})", user.getUserId());

        return mav;
    }

    @GetMapping("/admin_patientHelpRequest")
    public ModelAndView adminPatientHelpRequest() {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }

        ModelAndView mav = new ModelAndView();
        List<PatientHelpRequestDTO> patientHelpRequestDTOList = adminService.getPatientRequest();
        mav.addObject("patientHelpRequestDTOList", patientHelpRequestDTOList);
        mav.setViewName("Admin/admin_patientHelpRequest");

        logger.info("user try to access admin_caregiverHelpRequest  id:{})", user.getUserId());

        return mav;
    }

    @GetMapping("/admin_caregiverHelpRequest")
    public ModelAndView adminCaregiverHelpRequest() {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        List<CaregiverHelpRequestDTO> caregiverHelpRequestDTOList = adminService.getCaregiverRequest();
        mav.addObject("caregiverHelpRequestDTOList", caregiverHelpRequestDTOList);
        mav.setViewName("Admin/admin_caregiverHelpRequest");

        logger.info("user try to access admin_caregiverHelpRequest  id:{})", user.getUserId());

        return mav;
    }

    @GetMapping("/admin_blood_pressure/{userId}")
    public ModelAndView getBloogPressure(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }

        ModelAndView modelAndView = new ModelAndView();
        List<Tbl_blood_pressure_pulse> blood_pressure_pulseList = adminService.getBloodPressure(userId);
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        modelAndView.addObject("blood_pressure_pulseList", blood_pressure_pulseList);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.setViewName("Admin/admin_bloodPressure");

        logger.info("user try to access admin_bloodPressure  id:{}, PatientId:{})", user.getUserId(), userId);

        return modelAndView;
    }

    @GetMapping("/admin_natriumRecord/{userId}")
    public ModelAndView adminNatriumRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<NatriumCountDTO> natriumCountListSum = adminService.getNatriumCountWeek(userId);
        List<Tbl_natrium_record> natriumAsc = adminService.getNatriumAsc(userId);
        mav.addObject("patient", headerInfo);
        mav.addObject("natriumCountListSumLists", natriumCountListSum);
        mav.addObject("natriumAsc", natriumAsc);
        mav.setViewName("Admin/admin_natriumRecord");

        logger.info("user try to access admin_natriumRecord  id:{}, PatientId:{})", user.getUserId(), userId);

        return mav;
    }

    @GetMapping("/admin_weightRecord/{userId}")
    public ModelAndView adminWeightRecord(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }

        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<WeightAvgDTO> weightAvg = adminService.getWeightAvg(userId);
        List<Tbl_weight> weightAsc = adminService.getWeightAsc(userId);

        mav.addObject("weekAsc", weightAsc);
        mav.addObject("weekAvg", weightAvg);
        mav.addObject("patient", headerInfo);

        logger.info("user try to access admin_weightRecord  id:{}, PatientId:{})", user.getUserId(), userId);

        mav.setViewName("Admin/admin_weightRecord");

        return mav;
    }

    @GetMapping("/admin_button_count/{userId}")
    public ModelAndView getSymptomAll(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        ModelAndView modelAndView = new ModelAndView("Admin/admin_button_count");
        List<Tbl_button_count> counts = adminService.getButtonCountAsc(userId);
        modelAndView.addObject("counts", counts);
        modelAndView.addObject("patient", headerInfo);
        List<ButtonCountSumDTO> countSum = adminService.getButtonCount(userId);
        modelAndView.addObject("countSum", countSum);
        logger.info("user try to access admin_button_count  id:{}, PatientId:{})", user.getUserId(),
                userId);

        return modelAndView;
    }

    @PostMapping(value = "/logout")
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
        User user = getUser();
        ModelAndView mav = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.info("user try to logout id:{}", user.getUserId());
        mav.setViewName("Login/loginhtml?logout=true");
        return mav;
    }
}
