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
import withus.entity.Tbl_symptom_log;
import withus.service.SymptomService;
import withus.service.UserService;

@Controller
public class SymptomController extends BaseController{
    private final SymptomService symptomService;

    @Autowired
    public SymptomController(UserService userService, AuthenticationFacade authenticationFacade, SymptomService symptomService) {
        super(userService, authenticationFacade);
        this.symptomService = symptomService;
    }

    @GetMapping("/symptom")
    @Statistical
    public ModelAndView getSymptom() {
        ModelAndView modelAndView = new ModelAndView("symptom/symptom");
        String username = getUsername();
        List<Tbl_symptom_log> symptomList;
        symptomList = symptomService.getSymptomAllRecord(new RecordKey(username, LocalDate.now()), -1);
        modelAndView.addObject("symptom", symptomList);
        modelAndView.addObject("previousUrl", "/home");
        return modelAndView;
    }

    @PutMapping(value = "/symptom-history",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_symptom_log> getSymptom(@RequestBody Tbl_symptom_log tbl_symptom_log){
        String userId = getUsername();
        tbl_symptom_log.setPk(new RecordKey(userId,LocalDate.now()));
        Result.Code code;
        Tbl_symptom_log saved = null;
        try{
            saved = symptomService.upsertSymptomRecord(tbl_symptom_log);
            code = Result.Code.OK;
        }catch (Exception exception){
            log.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_symptom_log>builder()
                .code(code)
                .data(saved)
                .build();
    }
}
