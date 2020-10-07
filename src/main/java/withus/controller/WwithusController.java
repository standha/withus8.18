package withus.controller;

import java.time.LocalDate;
import java.util.List;

import com.google.gson.internal.$Gson$Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.wwithus.ChatBalloon;
import withus.dto.wwithus.WwithusEntryRequest;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
import withus.entity.User;
import withus.service.CountService;
import withus.service.UserService;
import withus.service.WwithusService;
import withus.util.Utility;

@Controller
public class WwithusController extends BaseController {
    private final WwithusService wwithusService;
    private final CountService countService;

    @Autowired
    public WwithusController(UserService userService, AuthenticationFacade authenticationFacade, WwithusService wwithusService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
        this.wwithusService = wwithusService;
    }

    @GetMapping("/wwithus")
    public ModelAndView getWwithus() {
        ModelAndView modelAndView = new ModelAndView("wwithus/wwithus.html");
        modelAndView.addObject("previousUrl", "/home");
        User user = getUser();
        modelAndView.addObject("userType", user.getType());
        if (user.getType() == User.Type.PATIENT) {
            Tbl_button_count count = countService.getCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        return modelAndView;
    }


    @GetMapping("/wwithus/histories")
    @ResponseBody
    public Result<List<ChatBalloon>> getHistories() {
        Result.Code code = Result.Code.OK;
        User user = getUser();
        if (user.getType() == User.Type.CAREGIVER) {
            user = userService.getUserByCaregiverId(user.getUserId());
        }
        LocalDate today = LocalDate.now();
        List<ChatBalloon> data;
        try {
            data = wwithusService.getWwithusEntryHistories(user, today);
            /*
             * TODO: 재진입 시에 취할 행동.
             *  의도한 것은
             *  "다시 들어와주셨네요. 저 위더스랑과의 대화를 다시 하고 싶으세요?" 등의 처리이나,
             *  현재의 설계로는 불가능할 것 같아 보류 (2020.09.14)
             */
			/*List<ChatBalloon> histories = wwithusService.getWwithusEntryHistories(user, today);
			if (histories.stream().anyMatch(ChatBalloon::isToTerminate)) {
				data = wwithusService.getReentrentChatBalloons(user, today);
			} else {
				data = histories;
			}*/
        } catch (Utility.NoWithusException noWithusException) {
            data = wwithusService.getCyaLaterChatBalloons(user);
        } catch (Utility.NoHisException noHisException) {
            data = wwithusService.getNoPatientContent(user);
        }

        logger.debug("User ({}) has {} history(ies) of today.", user, data.size());

        return Result.<List<ChatBalloon>>builder()
                .code(code)
                .data(data)
                .build();
    }

    @DeleteMapping("/wwithus/histories")
    @ResponseBody
    public Result.Code deleteHistories() {
        User user = getUser();
        LocalDate today = LocalDate.now();

        return wwithusService.deleteWwithusEntryHistories(user, today);
    }

    @PostMapping("/wwithus/request-next")
    @ResponseBody
    public Result<ChatBalloon> getNext(@RequestBody WwithusEntryRequest wwithusEntryRequest) {
        Result.Code code = Result.Code.ERROR;
        ChatBalloon data = null;

        try {
            User user = getUser();
            wwithusEntryRequest.setUser(user);

            LocalDate today = LocalDate.now();
            wwithusEntryRequest.setDate(today);

            data = wwithusService.getWwithusEntryAndSaveHistory(wwithusEntryRequest);
            code = Result.Code.OK;

        } catch (Utility.NoHisException nh) {
            User uesrTest = getUser();
            data = wwithusService.getNoHis(uesrTest);
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
        }

        return Result.<ChatBalloon>builder()
                .code(code)
                .data(data)
                .build();
    }
}
