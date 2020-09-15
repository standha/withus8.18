package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public ModelAndView getSymptom() throws NullPointerException{
        ModelAndView modelAndView = new ModelAndView("Symptom/symptom");
        String username = getUsername();

        if (symptomService.getSymptom(new RecordKey(username, LocalDate.now()))==null) {
            modelAndView.addObject("tired", 2);
            modelAndView.addObject("ankle", 2);
            modelAndView.addObject("breath", 2);
            modelAndView.addObject("cough", 2);
            modelAndView.addObject("previousUrl", "/home");
            return modelAndView;
        } else {
            Tbl_symptom_log symptom = symptomService.getSymptom(new RecordKey(username, LocalDate.now()));
            modelAndView.addObject("tired", symptom.getTired());
            modelAndView.addObject("ankle", symptom.getAnkle());
            modelAndView.addObject("breath", symptom.getOutofbreath());
            modelAndView.addObject("cough", symptom.getCough());
            modelAndView.addObject("previousUrl", "/home");
            return modelAndView;
        }

    }

    @GetMapping("/symptom-all-history")
    @Statistical
    public ModelAndView getSymptomAll(){
        ModelAndView modelAndView = new ModelAndView("Symptom/symptom-history");
        String username = getUsername();
        List<Tbl_symptom_log>symtomHistory;
        symtomHistory = symptomService.getSymptomRecord(username,-1);
        modelAndView.addObject("symptom",symtomHistory);
        modelAndView.addObject("previousUrl","/symptom");
        return modelAndView;
    }

    @PostMapping("/symptom")
    @ResponseBody
    public Result<Tbl_symptom_log> PostSymptom(@RequestBody Tbl_symptom_log tbl_symptom_log){
        String userId = getUsername();
        tbl_symptom_log.setPk(new RecordKey(userId,LocalDate.now()));
        Result.Code code;
        Tbl_symptom_log saved = null;
        try{
            saved = symptomService.upsertSymptomRecord(tbl_symptom_log);
            code = Result.Code.OK;
        }catch (Exception exception){
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_symptom_log>builder()
                .setCode(code)
                .setData(saved)
                .createResult();
    }
}