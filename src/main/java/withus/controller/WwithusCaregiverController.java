package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.ProgressKey;
import withus.entity.Tbl_patient_main_button_count;
import withus.entity.User;
import withus.entity.WithusHelpRequest;
import withus.service.CountService;
import withus.service.HomeService;
import withus.service.UserService;
import withus.service.WwithusCaregiverService;
import withus.util.Utility;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WwithusCaregiverController extends BaseController {
    private final WwithusCaregiverService wwithusCaregiverService;
    private final CountService countService;

    private final HomeService homeService;

    @Autowired
    public WwithusCaregiverController(UserService userService, HomeService homeService, AuthenticationFacade authenticationFacade, WwithusCaregiverService wwithusCaregiverService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
        this.wwithusCaregiverService = wwithusCaregiverService;
        this.homeService = homeService;
    }

    @GetMapping("/wwithus/caregiver")
    public ModelAndView getWwithus(HttpServletRequest request) {
        User user = getUser();

        logger.info("wwithus id:{}, type:{}, week:{}, url:{}", user.getUserId(), user.getType(), user.getWeek(), request.getRequestURL());

        ModelAndView modelAndView = new ModelAndView("wwithus/wwithus");
        modelAndView.addObject("previousUrl", "/home");
        modelAndView.addObject("userType", user.getType());

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        return modelAndView;
    }

    @PostMapping("/home/help-request")
    @ResponseBody
    public Result<WithusHelpRequest> postHelpRequest(@RequestBody String helpCode) {
        User user = getUser();
        LocalDateTime now = LocalDateTime.now();

        Result.Code code = Result.Code.ERROR;
        WithusHelpRequest withusHelpRequest = null;

        try {
            withusHelpRequest = homeService.createHelpRequest(user, now, helpCode.replaceAll("\"", ""));
            code = Result.Code.OK;
        } catch (Exception exception) {
//			log.error(exception.getLocalizedMessage(), exception);
        }

        return Result.<WithusHelpRequest>builder()
                .code(code)
                .data(withusHelpRequest)
                .build();
    }

    @GetMapping("/wwithus/caregiver/histories")
    @ResponseBody
    public Result<List<ChatBalloon>> getHistories(HttpServletRequest request) {
        Result.Code code = Result.Code.OK;
        User user = getUser();

        if (user.getType() == User.Type.CAREGIVER) {
            user = userService.getUserByCaregiverId(user.getUserId());
        }

        LocalDate today = LocalDate.now();
        List<ChatBalloon> data;

        try {
            data = wwithusCaregiverService.getWwithusEntryHistories(user, today);

            logger.info("histories id:{}, type:{}, week:{}, url:{}, withusHistory:{}", user.getUserId(), user.getType(), user.getWeek(), request.getRequestURL(), data.size());

            /*
             * TODO: 재진입 시에 취할 행동.
             *  의도한 것은
             *  "다시 들어와주셨네요. 저 위더스랑과의 대화를 다시 하고 싶으세요?" 등의 처리이나,
             *  현재의 설계로는 불가능할 것 같아 보류 (2020.09.14)
             */
			/*List<ChatBalloon> histories = wwithusCaregiverService.getWwithusEntryHistories(user, today);
			if (histories.stream().anyMatch(ChatBalloon::isToTerminate)) {
				data = wwithusCaregiverService.getReentrentChatBalloons(user, today);
			} else {
				data = histories;
			}*/
        } catch (Utility.NoWithusException noWithusException) {
            data = wwithusCaregiverService.getCyaLaterChatBalloons(user);
        } catch (Utility.NoHisException noHisException) {
            data = wwithusCaregiverService.getNoPatientContent(user);
        }

        logger.debug("User ({}) has {} history(ies) of today.", user, data.size());

        return Result.<List<ChatBalloon>>builder()
                .code(code)
                .data(data)
                .build();
    }

    @DeleteMapping("/wwithus/caregiver/histories")
    @ResponseBody
    public Result.Code deleteHistories() {
        User user = getUser();
        LocalDate today = LocalDate.now();
        logger.info("id:{}, type:{}", user.getUserId(), user.getType());

        return wwithusCaregiverService.deleteWwithusEntryHistories(user, today);
    }

    @PostMapping("/wwithus/caregiver/request-next")
    @ResponseBody
    public Result<ChatBalloon> getNext(@RequestBody WwithusEntryRequest wwithusEntryRequest) {
        Result.Code code = Result.Code.ERROR;
        ChatBalloon data = null;

        try {
            User user = getUser();
            wwithusEntryRequest.setUser(user);
            logger.info("request-next id:{}, type:{}, NextCode:{}", user.getUserId(), user.getType(), wwithusEntryRequest.getNextCode());
            LocalDate today = LocalDate.now();
            wwithusEntryRequest.setDate(today);
            data = wwithusCaregiverService.getWwithusEntryAndSaveHistory(wwithusEntryRequest);
            code = Result.Code.OK;

        } catch (Utility.NoHisException nh) {
            User uesrTest = getUser();
            data = wwithusCaregiverService.getNoHis(uesrTest);
            logger.error("request-next history none id:{}, type:{}, NextCode:{}", uesrTest.getUserId(), uesrTest.getType(), wwithusEntryRequest.getNextCode());
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
        }

        return Result.<ChatBalloon>builder()
                .code(code)
                .data(data)
                .build();
    }
}
