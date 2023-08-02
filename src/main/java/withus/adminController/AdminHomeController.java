package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.*;
import withus.dto.HeaderInfoDTO;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.dto.wwithus.*;
import withus.entity.*;
import withus.service.*;

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

    @GetMapping("/admin_home/patient")
    public ModelAndView viewHome(){
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }

        ArrayList<String> userFin = userService.getAllUserPlz();
        List<AllUserDTO> resultList = new ArrayList<>();
        ModelAndView mav = new ModelAndView();

        if (userFin != null) {
            // userFin.forEach((s)-> resultList.add(AllUserDTO.fromString(s)));
            for (String aUserFin : userFin) {
                resultList.add(AllUserDTO.fromString(aUserFin));
            }
        }
        mav.addObject("type","PATIENT");
        mav.addObject("user", resultList);
        mav.setViewName("Admin/admin_home");
        return mav;
    }
    @GetMapping("/admin_home/caregiver")
    public ModelAndView viewCaregiverHome(){
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }

        List<AllUserDTO> resultList = new ArrayList<>();

        ArrayList<String> userFin = userService.getAllUserCaregiver();

        ModelAndView mav = new ModelAndView();

        if (userFin != null) {
            // userFin.forEach((s)-> resultList.add(AllUserDTO.fromString(s)));
            for (String aUserFin : userFin) {
                resultList.add(AllUserDTO.fromString(aUserFin));
            }
        }
        mav.addObject("type","CAREGIVER");
        mav.addObject("user", resultList);
        mav.setViewName("Admin/admin_home");
        return mav;
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
        mav.addObject("info", headerInfo);
        User.Type type = adminService.getTypeInfo(userId);


        mav.addObject("type", type);
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();

        mav.setViewName("Admin/admin_center");

        logger.info("user try to access admin_center id:{}, PatientId:{})", user.getUserId(),
                userId);

        return mav;
    }
    @GetMapping("/admin_dashboard")
    public ModelAndView viewDashboard() {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }


        ModelAndView mav = new ModelAndView();
        List<UserGenderCountDTO> userGenderCountInfo = adminService.getUserGenderCountInfo();
        List<UserAgeCountDTO> userAgeCountInfo = adminService.getUserAgeCountInfo();
        List<UserRegisterCountDTO> userRegisterCountInfo = adminService.getUserRegisterCountInfo();
        List<UserWeekCountDTO> userWeekCountInfo = adminService.getUserWeekCountInfo();
        List<UserRelativeCountDTO> userRelativeCountInfo = adminService.getUserRelativeCountInfo();
        List<CaregiverButtonSumDTO> caregiverButtonSumInfo = adminService.getCaregiverButtonSumInfo();
        List<PatientButtonSumDTO> patientButtonSumInfo = adminService.getPatientButtonSumInfo();

        mav.addObject("admin", user.getUserId());
        mav.addObject("userGenderCountList",userGenderCountInfo);
        mav.addObject("userAgeCountList",userAgeCountInfo);
        mav.addObject("userRegisterCountList",userRegisterCountInfo);
        mav.addObject("userWeekCountList",userWeekCountInfo);
        mav.addObject("userRelativeCountList",userRelativeCountInfo);
        mav.addObject("caregiverButtonSumList",caregiverButtonSumInfo);
        mav.addObject("patientButtonSumList",patientButtonSumInfo);

        mav.setViewName("Admin/admin_dashboard");

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
        User.Type type = adminService.getTypeInfo(userId);

        mav.addObject("type", type);
        mav.addObject("weekAsc", moistureAsc);
        mav.addObject("weekAvg", moistureAvg);
        mav.addObject("info", headerInfo);
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
        User.Type type = adminService.getTypeInfo(userId);

        modelAndView.addObject("type", type);
        modelAndView.addObject("info", headerInfo);
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
        User.Type type = adminService.getTypeInfo(userId);

        modelAndView.addObject("type", type);
        modelAndView.addObject("info", headerInfo);
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
//        List<PillSumDTO> pillSum = adminService.getPillSum(userId);
//        List<Tbl_medication_record> pillAsc = adminService.getPillAsc(userId);
        User.Type type = adminService.getTypeInfo(userId);

        mav.addObject("type",type);
//        mav.addObject("pillSum", pillSum);
        mav.addObject("info", headerInfo);
//        mav.addObject("pillAsc", pillAsc);
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
        User.Type type = adminService.getTypeInfo(userId);

        modelAndView.addObject("type", type);
        modelAndView.addObject("blood_pressure_pulseList", blood_pressure_pulseList);
        modelAndView.addObject("info", headerInfo);
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
        User.Type type = adminService.getTypeInfo(userId);

        mav.addObject("type", type);
        mav.addObject("info", headerInfo);
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
        User.Type type = adminService.getTypeInfo(userId);

        mav.addObject("weekAsc", weightAsc);
        mav.addObject("weekAvg", weightAvg);
        mav.addObject("info", headerInfo);
        mav.addObject("type", type);

        logger.info("user try to access admin_weightRecord  id:{}, PatientId:{})", user.getUserId(), userId);

        mav.setViewName("Admin/admin_weightRecord");

        return mav;
    }
    @GetMapping("/admin_durationTime/{userId}")
    public ModelAndView adminDurationTime(@PathVariable("userId") String userId) {

        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        User.Type type = adminService.getTypeInfo(userId);

        if(type == User.Type.PATIENT){
            List<Tbl_patient_duration_time> dt = adminService.getPatientDurationTime(userId);
            mav.addObject("durationTimeAsc", dt);
        } else if(type == User.Type.CAREGIVER){
            List<Tbl_caregiver_duration_time> dt = adminService.getCaregiverDurationTime(userId);
            mav.addObject("durationTimeAsc", dt);
        }

        mav.addObject("info", headerInfo);
        mav.addObject("type", type);
        mav.setViewName("Admin/admin_durationTime");

        logger.info("user try to access admin_durationTime  Id:{}, getId:{})", user.getUserId(), userId);
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
        User.Type type = adminService.getTypeInfo(userId);
        modelAndView.addObject("type", type);
        modelAndView.addObject("info", headerInfo);
        if(type == User.Type.PATIENT){

            List<Tbl_patient_main_button_count> counts = adminService.getPatientMainButtonCountAsc(userId);
            modelAndView.addObject("counts", counts);

            List<PatientMainButtonCountSumDTO> countSum = adminService.getPatientMainButtonCount(userId);
            modelAndView.addObject("countSum", countSum);

        } else {
            List<Tbl_caregiver_main_button_count> counts = adminService.getCaregiverMainButtonCountAsc(userId);
            modelAndView.addObject("counts", counts);

            List<CaregiverMainButtonCountSumDTO> countSum = adminService.getCaregiverMainButtonCount(userId);
            modelAndView.addObject("countSum", countSum);
        }


        logger.info("user try to access admin_button_count  id:{}, PatientId:{})", user.getUserId(),
                userId);

        return modelAndView;
    }
    @GetMapping("/admin_withusRang/{userId}")
    public ModelAndView getWwithusHistoryAll(@PathVariable("userId") String userId) {
        User user = getUser();
        if (user.getType() != User.Type.ADMINISTRATOR) {
            throw new IllegalStateException(user.getUserId() + " is not Admin");
        }
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        ModelAndView modelAndView = new ModelAndView("Admin/admin_withusRang");
        User.Type type = adminService.getTypeInfo(userId);
        modelAndView.addObject("type", type);
        modelAndView.addObject("info", headerInfo);

        List<WwithusHistoryDTO> withusRang = adminService.getWwithusHistory(userId);
        modelAndView.addObject("withusRang", withusRang);

        logger.info("user try to access admin_withusRang  id:{}, getId:{})", user.getUserId(),
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
