package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.dto.Result;
import withus.entity.Tbl_guardian;
import withus.entity.Tbl_patient;
import withus.service.UserService;
import withus.util.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final UserService userService;

	@Autowired
	public LoginController(UserService userService){
		this.userService = userService;
	}

	@GetMapping({ "/", "/login" })
	public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/login");

		return modelAndView;
	}

	@GetMapping({ "/registerUser" })
	public ModelAndView getRegisterPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/register");
		Tbl_patient tbl_patient = new Tbl_patient();
		Tbl_guardian tbl_guardian = new Tbl_guardian();
		modelAndView.addObject("user", tbl_patient);
		modelAndView.addObject("guser", tbl_guardian);
		modelAndView.addObject("previousUrl", "LogIn/login");
		return modelAndView;
	}
	@GetMapping({ "/registerGuardianUser" })
	public ModelAndView getGuardianRegisterPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("LogIn/register");
		Tbl_patient tbl_patient = new Tbl_patient();
		Tbl_guardian tbl_guardian = new Tbl_guardian();
		modelAndView.addObject("guser", tbl_guardian);
		modelAndView.addObject("previousUrl", "LogIn/login");
		return modelAndView;
	}


	@PostMapping(value = "/saveUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Tbl_patient> putMember(@RequestBody Tbl_patient tbl_patient) {
		Tbl_patient savedUser = null;
		Result.Code code = Result.Code.ERROR;
		if (!isMissingMandatories(tbl_patient)) {
			try {
				Tbl_patient existId = userService.getUserById(tbl_patient.getUsername());
				Tbl_patient existContact = userService.getUserByContact(tbl_patient.getContact());
				Tbl_patient existgContact = userService.getUserByGcontact(tbl_patient.getGcontact());
				savedUser = userService.upsertUserEncodingPassword(tbl_patient);
				code = Result.Code.OK;

				if (existContact != null) {
					savedUser = existContact;
					code = Result.Code.ERROR_DUPLICATE_CONTACT;
				}

				if (existId != null) {
					savedUser = existId;
					code = Result.Code.ERROR_DUPLICATE_ID;
				}
				if (existgContact != null){
					savedUser = existgContact;
					code = Result.Code.ERROR_DUPLICATE_GCONTACT;
				}

			} catch (Exception exception) {
				logger.error(exception.getLocalizedMessage(), exception);
				code = Result.Code.ERROR_DATABASE;
			}
		}


		return Result.<Tbl_patient>builder()
			.setCode(code)
			.setData(savedUser)
			.createResult();
	}

	public boolean isMissingMandatories(Tbl_patient tbl_patient){

		if (Utility.nullOrEmptyOrSpace(tbl_patient.getPatientId()) ||
			Utility.nullOrEmptyOrSpace(tbl_patient.getPassword()) ||
			Utility.nullOrEmptyOrSpace(tbl_patient.getName()) ||
			Utility.nullOrEmptyOrSpace(tbl_patient.getContact()) ||
			Utility.nullOrEmptyOrSpace(tbl_patient.getGcontact())){
			return true;
		}
		return false;
	}


	@PostMapping(value = "/saveGuser", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Result<Tbl_guardian> putGuardian(@RequestBody Tbl_guardian tbl_guardian) {

		Tbl_guardian savedgUser = null;
		Result.Code code = Result.Code.ERROR;

		System.out.println("11111111111 "+ tbl_guardian.getName());
		System.out.println("22222222222 "+ tbl_guardian.getPassword());
		//savedUser = tbl_userService.upsertGuardian(tbl_guardian);
		if (!isMissingMandatories_g(tbl_guardian)) {
		//	savedgUser = tbl_userService.upsertGuardian(tbl_guardian);
			try {
				Tbl_guardian existgId = userService.getUserByGid(tbl_guardian.getUsername());
				Tbl_guardian existgContact = userService.getUserByGtell(tbl_guardian.getGtell());
				savedgUser = userService.upsertGuardian(tbl_guardian);
				code = Result.Code.OK;

				if (existgContact != null) {
					savedgUser = existgContact;
					code = Result.Code.ERROR_DUPLICATE_GTELL;
				}
				if (existgId != null) {
					savedgUser = existgId;
					code = Result.Code.ERROR_DUPICATE_GID;
				}

			} catch (Exception exception) {
				logger.error(exception.getLocalizedMessage(), exception);
				code = Result.Code.ERROR_DATABASE;
			}
		}else if(isMissingMandatories_g(tbl_guardian)){
			System.out.println("Missing Error");
		}

		return Result.<Tbl_guardian>builder()
				.setCode(code)
				.setData(savedgUser)
				.createResult();
	}

	public boolean isMissingMandatories_g(Tbl_guardian tbl_guardian){

		if (Utility.nullOrEmptyOrSpace(tbl_guardian.getGuardianId()) ||
				Utility.nullOrEmptyOrSpace(tbl_guardian.getPassword()) ||
				Utility.nullOrEmptyOrSpace(tbl_guardian.getGtell())){
			return true;
		}
		return false;
	}
	////////////////
}
