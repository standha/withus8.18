package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.ProgressKey;
import withus.entity.Tbl_button_count;
import withus.entity.User;
import withus.service.CountService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ButtonCountController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final CountService countService;

    @Autowired
    public ButtonCountController(AuthenticationFacade authenticationFacade, UserService userService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }

    @PutMapping(value = "/button-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_button_count> getSymptomCount(@RequestBody Tbl_button_count count, HttpServletRequest request) {
        String userId = getUsername();
        User user = userService.getUserById(userId);
        count.setKey(new ProgressKey(userId, user.getWeek()));
        logger.info("id:{}, week:{}, alarm:{}, blood_pressure:{}, disease_info:{}, exercise:{}, goal:{}, helper:{}, level:{}, natrium-moisture:{}, symptom:{}, weight:{}, chat:{}"
                , count.getKey().getId(), count.getKey().getWeek(), count.getAlarm(), count.getBloodPressure(), count.getDiseaseInfo(), count.getExercise(), count.getGoal(), count.getHelper(),
                count.getLevel(), count.getNatriumMoisture(), count.getSymptom(), count.getWeight(), count.getWithusRang());
        Result.Code code;
        Tbl_button_count saved = null;
        try {
            saved = countService.upsertCount(count);
            code = Result.Code.OK;
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_button_count>builder()
                .code(code)
                .data(saved)
                .build();
    }
}