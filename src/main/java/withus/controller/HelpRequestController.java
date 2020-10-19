package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.HelperRequestService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class HelpRequestController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final HelperRequestService helperRequestService;

    @Autowired
    public HelpRequestController(AuthenticationFacade authenticationFacade, UserService userService, HelperRequestService helperRequestService) {
        super(userService, authenticationFacade);
        this.helperRequestService = helperRequestService;
    }

    @PostMapping(value = "/helper-request", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_helper_request> temp(@RequestBody Tbl_helper_request tbl_helper_request) {
        User user = getUser();
        tbl_helper_request.setPk(new TimeKey(user.getUserId(), LocalDate.now(), LocalTime.now()));
        Result.Code code;
        Tbl_helper_request saved = null;

        logger.info("id:{}, type:{}, date:{}, time:{}", user.getUserId(), user.getType(), tbl_helper_request.getPk().getDate(), tbl_helper_request.getPk().getTime());

        try {
            if(user.getType() == User.Type.PATIENT){
                saved = helperRequestService.upsertHelperRequest(tbl_helper_request);
                code = Result.Code.OK;
            }else
                throw new IllegalArgumentException("Caregiver try input data, [warn]");
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);

            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_helper_request>builder()
                .code(code)
                .data(saved)
                .build();
    }
}
