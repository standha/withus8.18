package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
import withus.dto.HelpRequestDTO;
import withus.dto.MoistureAvgDTO;
import withus.dto.PillSumDTO;
import withus.entity.Tbl_medication_record;
import withus.entity.Tbl_mositrue_record;
import withus.service.AdminService;
import withus.service.GoalService;
import withus.service.MoistureNatriumService;
import withus.service.UserService;

import java.time.Month;
import java.util.List;

@Controller
public class AdminHomeController extends AdminBaseController
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final GoalService goalService;
	private final MoistureNatriumService moistureNatriumService;
	private final AdminService adminService;

	@Autowired
	public AdminHomeController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, AdminService adminService,
							   MoistureNatriumService moistureNatriumService)
	{
		super(userService, authenticationFacade);
		this.goalService = goalService;
		this.moistureNatriumService = moistureNatriumService;
		this.adminService = adminService;
	}

	@GetMapping({ "/adminHome" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{
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
	public ModelAndView adminPillRecord(@PathVariable("userId") String userId){
		ModelAndView mav = new ModelAndView();
		HeaderInfoDTO headerInfo = adminService.getHeaderInfo(userId);
		List<PillSumDTO> pillSum = adminService.getPillSum(userId);
		List<Tbl_medication_record> pillAsc = adminService.getPillAsc(userId);
		mav.addObject("pillSum",pillSum);
		mav.addObject("patient", headerInfo);
		mav.addObject("pillAsc", pillAsc);
		mav.setViewName("/Admin/admin_pillRecord");
		return mav;
	 }

	@GetMapping("/adimn_withusHelpRequest")
	public ModelAndView adminWithusHelpRequest(){
		ModelAndView mav = new ModelAndView();
		List<HelpRequestDTO> helpRequestAsc = adminService.getHelpRequestAsc();
		mav.addObject("helpRequestAsc",helpRequestAsc);
		mav.setViewName("/Admin/admin_withusHelpRequest");
		return mav;
	}

	@PostMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/Login/admin_login?logout";
	}
}
