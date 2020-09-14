package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.entity.RecordKey;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.entity.User.Type;
import withus.service.GoalService;
import withus.service.UserService;

import java.time.LocalDate;

@Controller
public class CenterController extends BaseController
{
	private final GoalService goalService;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public CenterController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService)
	{
		super(userService, authenticationFacade);
		this.goalService = goalService;
	}

	@GetMapping({ "/center" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response)
	{

		logger.info("center");
		User user = getUser();
		ModelAndView modelAndView = new ModelAndView();

		if (user.getType().equals(Type.ADMINISTRATOR))
		{
			modelAndView.setViewName("/Admin/admin_Home");
		}
		else
		{
			modelAndView.addObject("type", user.getType());
			System.out.println(user.getType());
			modelAndView.setViewName("home");
		}

		modelAndView.addObject("user", user);

		Tbl_goal goalRecord;
		String userId = getUsername();
		String goal = null;

		goalRecord = goalService.getGoalDateRecord(new RecordKey(userId, LocalDate.now()), 0);
		if(goalRecord == null){
			goal = "목표를 설정해 주세요";
		}
		else {
			switch (goalRecord.getGoal()){
				case 1:
					goal = "매일 정해진 시간 약 복용";
					break;

				case 2:
					goal = "매일 혈압과 맥박 측정";
					break;

				case 3:
					goal = "매일 체중 측정";
					break;

				case 4:
					goal = "주 3회 이상 증상일지 기록";
					break;

				case 5:
					goal = "매일 증상일지 기록";
					break;

				case 6:
					goal = "주 3회 이상 식사 시 염분 측정";
					break;

				case 7:
					goal = "매일 식사 시 염분 측정";
					break;

				case 8:
					goal = "주 1회, 최소 30분 이상 운동";
					break;

				case 9:
					goal = "주 3회, 최소 30분 이상 운동";
					break;

				default:
					goal = "목표를 설정해 주세요";
					break;
			}
		}
		modelAndView.addObject("goalThisWeek", goal);
		System.out.println(goal);

		return modelAndView;
	}

//	@GetMapping("/goal")
//	@Statistical
//	public ModelAndView getGoal(){
//		ModelAndView modelAndView = new ModelAndView("goal/goal");
//		modelAndView.addObject("previousUrl", "/home");
//
//		Tbl_goal goalRecord;
//		String userId = getUsername();
//		String goal = null;
//
//		goalRecord = goalService.getGoalDateRecord(new RecordKey(userId, LocalDate.now()), 0);
//		if(goalRecord == null){
//			goal = "목표를 설정해 주세요";
//		}
//		else {
//			if (goalRecord.getGoal() == 1) {
//				goal = "목표 1설정";
//			} else if (goalRecord.getGoal() == 2) {
//				goal = "목표 2설정";
//			}
//			else{
//				goal = "목표테스트";
//			}
//		}
//		modelAndView.addObject("goalThisWeek", goal);
//		System.out.println(goal);
//		return modelAndView;
//	}

}
