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
public class AdminHomeController extends AdminBaseController {
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

    @GetMapping({"/adminHome"})
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/Admin/admin_home");
        return modelAndView;
    }

    @GetMapping("/user/{userId}")
    public ModelAndView viewPatient(@PathVariable("userId") String userId) {
        // @pathVariable, @ParameterValue, @Header
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        mav.addObject("patient", headerInfo);
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();

        mav.setViewName("/Admin/admin_center");

        return mav;
    }

    @GetMapping("/admin_moistureRecord/{userId}")
    public ModelAndView adminMoistureRecord(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<MoistureAvgDTO> moistureAvg = adminService.getMoistureAvg(userId) == null ? null : adminService.getMoistureAvg(userId);
        List<Tbl_mositrue_record> moistureAsc = adminService.getMoistureAsc(userId) == null ? null : adminService.getMoistureAsc(userId);
        mav.addObject("weekAsc", moistureAsc);
        mav.addObject("weekAvg", moistureAvg);
        mav.addObject("patient", headerInfo);
        mav.setViewName("/Admin/admin_moistureRecord");

        return mav;
    }

    @GetMapping("/admin_symptomRecord/{userId}")
    public ModelAndView adminSymptomRecord(@PathVariable("userId") String userId){
        ModelAndView modelAndView = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<Tbl_symptom_log> symptom = adminService.getSymptom(userId) == null ? null : adminService.getSymptom(userId);
        List<SymptomAvgDTO> symptomAvg = adminService.getSymptomAvg(userId) == null ? null : adminService.getSymptomAvg(userId);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.addObject("symptom", symptom);
        modelAndView.addObject("symptomAvg",symptomAvg);
        modelAndView.setViewName("/Admin/admin_symptomRecord");

        return modelAndView;
    }

    @GetMapping("/admin_exerciseRecord/{userId}")
    public ModelAndView adminExerciseRecord(@PathVariable("userId") String userId){
        ModelAndView modelAndView = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<Tbl_Exercise_record> exercise = adminService.getExercise(userId);
        List<ExerciseDTO> exerciseAvg = adminService.getExerciseAvg(userId);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.addObject("exercise", exercise);
        modelAndView.addObject("exerciseAvg", exerciseAvg);
        modelAndView.setViewName("/Admin/admin_exerciseRecord");

        return modelAndView;
    }

    @GetMapping("/admin_pillRecord/{userId}")
    public ModelAndView adminPillRecord(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<PillSumDTO> pillSum = adminService.getPillSum(userId);
        List<Tbl_medication_record> pillAsc = adminService.getPillAsc(userId);
        mav.addObject("pillSum", pillSum);
        mav.addObject("patient", headerInfo);
        mav.addObject("pillAsc", pillAsc);
        mav.setViewName("/Admin/admin_pillRecord");

        return mav;
    }

    @GetMapping("/admin_withusHelpRequest")
    public ModelAndView adminWithusHelpRequest() {
        ModelAndView mav = new ModelAndView();
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();
        mav.addObject("helpRequestAsc", helpRequestAsc);
        mav.setViewName("/Admin/admin_withusHelpRequest");

        return mav;
    }

    @GetMapping("/admin_patientHelpRequest")
    public ModelAndView adminPatientHelpRequest() {
        ModelAndView mav = new ModelAndView();
        List<PatientHelpRequestDTO> patientHelpRequestDTOList = adminService.getPatientRequest();
        mav.addObject("patientHelpRequestDTOList", patientHelpRequestDTOList);
        mav.setViewName("/Admin/admin_patientHelpRequest");

        return mav;
    }

    @GetMapping("/admin_caregiverHelpRequest")
    public ModelAndView adminCaregiverHelpRequest() {
        ModelAndView mav = new ModelAndView();
        List<CaregiverHelpRequestDTO> caregiverHelpRequestDTOList = adminService.getCaregiverRequest();
        mav.addObject("caregiverHelpRequestDTOList", caregiverHelpRequestDTOList);
        mav.setViewName("/Admin/admin_caregiverHelpRequest");

        return mav;
    }

    @GetMapping("/admin_blood_pressure/{userId}")
    public ModelAndView getBloogPressure(@PathVariable("userId") String userId){
        ModelAndView modelAndView = new ModelAndView();
        List<Tbl_blood_pressure_pulse> blood_pressure_pulseList = adminService.getBloodPressure(userId);
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        modelAndView.addObject("blood_pressure_pulseList", blood_pressure_pulseList);
        modelAndView.addObject("patient", headerInfo);
        modelAndView.setViewName("/Admin/admin_bloodPressure");
        return modelAndView;
    }

    @GetMapping("/admin_natriumRecord/{userId}")
    public ModelAndView adminNatriumRecord(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<NatriumCountDTO> natriumCountListSum = adminService.getNatriumCountWeek(userId);
        List<Tbl_natrium_record> natriumAsc = adminService.getNatriumAsc(userId);
        mav.addObject("patient", headerInfo);
        mav.addObject("natriumCountListSumLists", natriumCountListSum);
        mav.addObject("natriumAsc",natriumAsc);
        mav.setViewName("/Admin/admin_natriumRecord");
        return mav;
    }

    @GetMapping("/admin_weightRecord/{userId}")
    public ModelAndView adminWeightRecord(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        List<WeightAvgDTO> weightAvg = adminService.getWeightAvg(userId);
        List<Tbl_weight> weightAsc = adminService.getWeightAsc(userId);
        weightAsc.forEach(s -> s.getWeight());
        weightAvg.forEach(s -> s.getWeight());

        mav.addObject("weekAsc", weightAsc);
        mav.addObject("weekAvg", weightAvg);
        mav.addObject("patient", headerInfo);
        mav.setViewName("/Admin/admin_weightRecord");

        return mav;
    }

    @GetMapping("/admin_button_count/{userId}")
    public ModelAndView getSymptomAll(@PathVariable("userId") String userId) {
        int alarm = 0;
        int blood =0;
        int disease_info = 0 ;
        int exercise = 0 ;
        int goal = 0 ;
        int helper =0;
        int level =0;
        int natrium =0;
        int symptom =0;
        int weight =0;
        int chat =0;
        HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
        ModelAndView modelAndView = new ModelAndView("Admin/admin_button_count");
        List<Tbl_button_count> counts = new ArrayList<>();
        for(int i = 1 ; i <=  24 ; i++){
            Tbl_button_count count = countService.getCount(new ProgressKey(userId,i));
            counts.add(count);

            alarm = alarm + count.getAlarm();
            blood = blood + count.getBloodPressure();
            disease_info = disease_info + count.getDiseaseInfo();
            exercise = exercise + count.getExercise();
            goal = goal + count.getGoal();
            helper = helper + count.getHelper();
            level = level + count.getLevel();
            natrium = natrium + count.getNatriumMoisture();
            symptom = symptom + count.getSymptom();
            weight = weight + count.getWeight();
            chat = chat + count.getWithusRang();
        }
        modelAndView.addObject("goal",goal);
        modelAndView.addObject("level",level);
        modelAndView.addObject("alarm",alarm);
        modelAndView.addObject("blood",blood);
        modelAndView.addObject("exercise",exercise);
        modelAndView.addObject("natrium",natrium);
        modelAndView.addObject("weight",weight);
        modelAndView.addObject("symptom",symptom);
        modelAndView.addObject("disease_info",disease_info);
        modelAndView.addObject("chat",chat);
        modelAndView.addObject("helper",helper);
        modelAndView.addObject("counts",counts);
        modelAndView.addObject("patient", headerInfo);
        return modelAndView;
    }

    @PostMapping(value = "/logout")
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/Login/loginhtml?logout=true");
        return mav;
    }
}
