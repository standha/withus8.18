package withus.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.RecordKey;
import withus.entity.Tbl_weight;
import withus.entity.User;
import withus.service.UserService;
import withus.service.WeightService;

@Controller
public class WeightController extends BaseController {
    private final WeightService weightService;

    @Autowired
    public WeightController(UserService userService, AuthenticationFacade authenticationFacade, WeightService weightService) {
        super(userService, authenticationFacade);
        this.weightService = weightService;
    }

    @GetMapping("/weight")
    @Statistical
    public ModelAndView getWeight() {
        ModelAndView modelAndView = new ModelAndView("weight/weight");
        modelAndView.addObject("previousUrl", "/home");
        String username = getUsername();
        List<Tbl_weight> weightRecord;

        weightRecord = weightService.getWeightDateRecord(new RecordKey(username, LocalDate.now()), 0);

        User user = getUser();

        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("weightRecord", weightRecord);
        return modelAndView;
    }

    @GetMapping("/weight-history")
    @Statistical
    public ModelAndView getWeightHistory(){
        ModelAndView modelAndView = new ModelAndView("weight/weight-history");
        modelAndView.addObject("previousUrl", "/weight");
        String username = getUsername();
        List<Tbl_weight> weightRecord;

        weightRecord = weightService.getWeightRecord(username, 0);

        modelAndView.addObject("weightRecord", weightRecord);
        return modelAndView;
    }


    @PutMapping(value = "/weight-history", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_weight> getWeight(@RequestBody Tbl_weight tbl_weight){
        String userId = getUsername();
        tbl_weight.setPk(new RecordKey(userId, LocalDate.now()));
        Result.Code code;
        Tbl_weight saved = null;
        try{
            saved = weightService.upsertWeightRecord(tbl_weight);
            code = Result.Code.OK;
        }catch(Exception exception){
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_weight>builder()
                .setCode(code)
                .setData(saved)
                .createResult();
    }


}
