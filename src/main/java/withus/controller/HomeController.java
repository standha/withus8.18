package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.entity.RecordKey;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.GoalService;
import withus.service.UserService;

import java.time.LocalDate;

@Controller
public class HomeController extends BaseController{

	private final GoalService goalService;

	@Autowired
	public HomeController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService) {
		super(userService, authenticationFacade);
		this.goalService = goalService;
	}

	@GetMapping({ "/home" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("home");
		User user = getUser();
		modelAndView.addObject("user", user);

		// TODO: to be removed
		System.out.printf("caretaker: %s\n", getCaretaker());

		modelAndView.addObject("user", user);

		Tbl_goal goalRecord;
		String userId = getUsername();
		String goal = null;

//		goalRecord = goalService.getGoalIdRecord(userId, 0);
//		if(goalRecord == null){
//			goal = "목표를 설정해 주세요";
//		}
//		else {
//			switch (goalRecord.getGoal()){
//				case 1:
//					goal = "매일 정해진 시간 약 복용";
//					break;
//
//				case 2:
//					goal = "매일 혈압과 맥박 측정";
//					break;
//
//				case 3:
//					goal = "매일 체중 측정";
//					break;
//
//				case 4:
//					goal = "주 3회 이상 증상일지 기록";
//					break;
//
//				case 5:
//					goal = "매일 증상일지 기록";
//					break;
//
//				case 6:
//					goal = "주 3회 이상 식사 시 염분 측정";
//					break;
//
//				case 7:
//					goal = "매일 식사 시 염분 측정";
//					break;
//
//				case 8:
//					goal = "주 1회, 최소 30분 이상 운동";
//					break;
//
//				case 9:
//					goal = "주 3회, 최소 30분 이상 운동";
//					break;
//
//				default:
//					goal = "목표를 설정해 주세요";
//					break;
//			}
//		}
		modelAndView.addObject("goalThisWeek", goal);
		System.out.println(goal);

		return modelAndView;
	}
}
