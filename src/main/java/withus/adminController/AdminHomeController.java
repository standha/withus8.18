package withus.adminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.querydsl.core.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.wwithus.HeaderInfoDTO;
import withus.entity.Tbl_goal;
import withus.entity.User;
import withus.service.GoalService;
import withus.service.UserService;

import java.util.List;

@Controller
public class AdminHomeController extends AdminBaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final GoalService goalService;

    @Autowired
    public AdminHomeController(AuthenticationFacade authenticationFacade, UserService userService, GoalService goalService) {
        super(userService, authenticationFacade);
        this.goalService = goalService;
    }

    @GetMapping({"/adminHome"})
    public ModelAndView getMain(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/Admin/admin_home");
        return modelAndView;
    }

    @GetMapping("/user/{userId}")
    public ModelAndView viewPatient(@PathVariable("userId") String userId) {
        // @pathVariable, @ParameterValue, @Header
        User patient = userService.getUserById(userId);
        ModelAndView mav = new ModelAndView();
        Tbl_goal goal = goalService.getGoalId(userId);
        List<Tuple> moistureAvg = userService.getMoistureAvg(userId);
        for (int i = 0; i < moistureAvg.size(); i++) {
            System.out.println("moistureAvg.get(0) = " + moistureAvg.get(i));
        }

        mav.addObject("patient", patient);
        mav.addObject("goal", goal.getGoal());
        mav.setViewName("/Admin/admin_center");
        return mav;
    }

    @GetMapping("/admin_pillHistory/{userId}")
    public ModelAndView adminPillHistory(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        HeaderInfoDTO headerInfo = userService.getHeaderInfo(userId);
        List<Tuple> moistureAvg = userService.getMoistureAvg(userId);
        System.out.println("moistureAvg.size() = " + moistureAvg.size());
        System.out.println("moistureAvg = " + moistureAvg);
        System.out.println(" hell- ");
        for (int i = 0; i < moistureAvg.size(); i++) {
            System.out.println("moistureAvg.get(0) = " + moistureAvg.get(i));
        }
        mav.addObject("patient", headerInfo);
        mav.setViewName("/Admin/admin_pillHistory");
        return mav;

    }

    @PostMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/Login/admin_login?logout";
    }
}
