package withus.controller;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.User;
import withus.entity.WithusHelpRequest;
import withus.service.HomeService;
import withus.service.UserService;

@Controller
public class HomeController extends BaseController {
    private final HomeService homeService;

    @Autowired
    public HomeController(AuthenticationFacade authenticationFacade, UserService userService, HomeService homeService) {
        super(userService, authenticationFacade);

        this.homeService = homeService;
    }

    @GetMapping({"/home"})
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("home");
        User user = getUser();
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PostMapping("/home/help-request")
    @ResponseBody
    public Result<WithusHelpRequest> postHelpRequest() {
        User user = getUser();
        LocalDateTime now = LocalDateTime.now();

        Result.Code code = Result.Code.ERROR;
        WithusHelpRequest withusHelpRequest = null;
        try {
            withusHelpRequest = homeService.createHelpRequest(user, now);
            code = Result.Code.OK;
        } catch (Exception exception) {
//			log.error(exception.getLocalizedMessage(), exception);
        }

        return Result.<WithusHelpRequest>builder()
                .code(code)
                .data(withusHelpRequest)
                .build();
    }
}
