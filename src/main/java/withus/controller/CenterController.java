package withus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.AllUserDTO;
import withus.entity.*;
import withus.entity.User.Type;
import withus.service.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class CenterController extends BaseController
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final GoalService goalService;
	private final CountService countService;
	private final HelperRequestService helperRequestService;
	private final AdminService adminService;

	@Autowired
	public CenterController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService, AdminService adminService,
							CountService countService, HelperRequestService helperRequestService)
	{
		super(userService, authenticationFacade);
		this.goalService = goalService;
		this.countService = countService;
		this.helperRequestService = helperRequestService;
		this.adminService = adminService;
	}

	@GetMapping({ "/center" })
	public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) String token)
	{
		User user = getUser();
/*		List<Tuple> moistureTesst = userService.getMoisture(user.getUserId());
		System.out.println(moistureTesst);
		for(int index =0; index< moistureTesst.size(); index++){
			System.out.println("moistureTesst = " + moistureTesst.get(index));
		}*/

		logger.info("url:{}, id:{}, type:{}, level:{}, week:{}", request.getRequestURL(), user.getUserId(), user.getType(), user.getLevel(), user.getWeek());
		if(user.getAppToken() != null){
			if(token != null) {
				if (user.getAppToken().equals(token) == false) {
					Result.Code code;
					user.setAppToken(token);
					try {
						user = userService.upsertUser(user);
						code = Result.Code.OK;
					} catch (Exception exception) {
						logger.error(exception.getLocalizedMessage(), exception);
						code = Result.Code.ERROR_DATABASE;
					}
					Result.<User>builder()
							.code(code)
							.data(user)
							.build();
				}
			}
		}else{
			if(token != null){
				Result.Code code;
				user.setAppToken(token);
				try {
					user = userService.upsertUser(user);
					code = Result.Code.OK;
				} catch (Exception exception) {
					logger.error(exception.getLocalizedMessage(), exception);
					code = Result.Code.ERROR_DATABASE;
				}
				Result.<User>builder()
						.code(code)
						.data(user)
						.build();
			}
		}

		ModelAndView modelAndView = new ModelAndView();

		if (user.getType().equals(Type.ADMINISTRATOR)){
			List<AllUserDTO> resultList = new ArrayList<>();
			ArrayList<String> userFin = adminService.getAllUserPlz();
			for (String aUserFin : userFin) {
				resultList.add(AllUserDTO.fromString(aUserFin));
			}
			modelAndView.addObject("user", resultList);
			modelAndView.setViewName("/Admin/admin_Home");
		}
		else if (user.getType().equals(Type.PATIENT)) {
			//환자 로그인 중
			if(user.getWeek() == 0){
				modelAndView.setViewName("home_0week");
			}
			else {
				Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
				modelAndView.setViewName("home");
				modelAndView.addObject("count", count);
				modelAndView.addObject("type", user.getType());
				modelAndView.addObject("week", user.getWeek());
			}
		}
		else{	//보호자 로그인 중
			if(getCaretaker().getWeek() == 0) {
				modelAndView.setViewName("home_0week");
			}
			else{
				modelAndView.setViewName("home");
				modelAndView.addObject("type", user.getType());
				modelAndView.addObject("week", getCaretaker().getWeek());
			}
		}

		if(user.getType()== Type.CAREGIVER || user.getType()== Type.PATIENT){
			modelAndView.addObject("goalNow",getGoalNow(getConnectId()));
			modelAndView.addObject("level", ViewLevel(user));
			modelAndView.addObject("user", user);
		}
		return modelAndView;
	}

	public Integer ViewLevel(User user){
		Integer level = 1;
		switch (user.getType()){
			case PATIENT:
				level = user.getLevel();
				level = level % 4;
				break;
			case CAREGIVER:
				level = getCaretaker().getLevel();
				System.out.println("보호자의 환자 레벨 : " + level);
				level = level % 4;
				break;
		}
		return level;
	}

	public String getGoalNow(String username){
		Integer goalCheck = goalService.getGoalId(username).getGoal();
		String goalNow = "";
		switch (goalCheck){
			case 0:
				goalNow = "이번주 목표를 설정해봐요!";
				break;
			case 1:
				goalNow = "매일 정해진 시간 약 복용";
				break;
			case 2:
				goalNow = "매일 혈압과 맥박 측정";
				break;
			case 3:
				goalNow = "매일 체중 측정";
				break;
			case 4:
				goalNow = "주 3회 이상 증상일지 기록";
				break;
			case 5:
				goalNow = "매일 증상일지 기록";
				break;
			case 6:
				goalNow = "주 3회 이상 식사 시 염분 측정";
				break;
			case 7:
				goalNow = "매일 식사 시 염분 측정";
				break;
			case 8:
				goalNow = "주 1회, 최소 30분 이상 운동";
				break;
			case 9:
				goalNow = "주 3회, 최소 30분 이상 운동";
				break;
		}
		return goalNow;
	}

	@PutMapping(value = "/button-count",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Tbl_button_count> getSymptomCount(@RequestBody Tbl_button_count tbl_button_count){
		String userId = getUsername();
		User user = userService.getUserById(userId);
		tbl_button_count.setKey(new ProgressKey(userId, user.getWeek()));
		Result.Code code;
		Tbl_button_count saved = null;
		try{
			saved = countService.upsertCount(tbl_button_count);
			code = Result.Code.OK;
		}catch (Exception exception){
			logger.error(exception.getLocalizedMessage(), exception);
			code = Result.Code.ERROR_DATABASE;
		}
		return Result.<Tbl_button_count>builder()
				.code(code)
				.data(saved)
				.build();
	}

	@PostMapping(value = "helper-request",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Tbl_helper_request> temp(@RequestBody Tbl_helper_request tbl_helper_request){
		String userId = getUsername();
		User user = userService.getUserById(userId);
		tbl_helper_request.setPk(new TimeKey(userId,LocalDate.now(),LocalTime.now()));
		Result.Code code;
		Tbl_helper_request saved = null;
		try{
			saved = helperRequestService.upsertHelperRequest(tbl_helper_request);
			code = Result.Code.OK;
		}catch (Exception exception){
			logger.error(exception.getLocalizedMessage(), exception);
			code = Result.Code.ERROR_DATABASE;
		}
		return Result.<Tbl_helper_request>builder()
				.code(code)
				.data(saved)
				.build();
	}


}
