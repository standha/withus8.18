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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.HeaderInfoDTO;
import withus.dto.HelpRequest.CaregiverHelpRequestDTO;
import withus.dto.HelpRequest.PatientHelpRequestDTO;
import withus.dto.HelpRequestDTO;
import withus.dto.MoistureAvgDTO;
import withus.dto.PillSumDTO;
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

    @GetMapping("/adimn_withusHelpRequest")
    public ModelAndView adminWithusHelpRequest() {
        ModelAndView mav = new ModelAndView();
        List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();
        mav.addObject("helpRequestAsc", helpRequestAsc);
        mav.setViewName("/Admin/admin_withusHelpRequest");

        List<CaregiverHelpRequestDTO> caregiverHelpRequestDTOList = adminService.getCaregiverRequest();
        for (CaregiverHelpRequestDTO test : caregiverHelpRequestDTOList) {

            System.out.println("아이디 : " + test.getId());
            System.out.println("이름 : " + test.getName());
            System.out.println("연락처 : " + test.getContact());
            System.out.println("환자 이름 : " + test.getPatientName());
            System.out.println("환자 아이디 : " + test.getPatientId());
            System.out.println("환자 번호 : " + test.getPatientContact());
            System.out.println("날짜 : " + test.getRequestDate());
            System.out.println("시간 : " + test.getRequestTime());

        }
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
