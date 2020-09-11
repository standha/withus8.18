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

        List<Tbl_weight> weightRecord;
        List<Tbl_weight> weightRecord2;
        Tbl_weight weightRecord3;


        String userId = getUsername();
        User user = getUser();

        weightRecord3 = weightService.getWeight(new RecordKey(userId, LocalDate.now()), 0);

        float testweight = 0;

        modelAndView.addObject("type", user.getType());

        if(user.getType() == User.Type.PATIENT){
            weightRecord = weightService.getWeightDateRecord(new RecordKey(userId, LocalDate.now()), 0);
            weightRecord2 = weightService.getWeightRecord(userId,0);

            System.out.println(weightRecord3);

            modelAndView.addObject("weightRecordToday", weightRecord);
            modelAndView.addObject("weightTest", weightRecord2);

            if(weightRecord3 == null) {
                testweight = 0;
                modelAndView.addObject("testWeight", testweight);
            }
            else{
                testweight = weightRecord.get(weightRecord.size()-1).getWeight();
                modelAndView.addObject("testWeight", testweight);
            }
            System.out.println(testweight);
        }
        else if(user.getType() == User.Type.CAREGIVER){
            User patient = getCaretaker();
            weightRecord = weightService.getWeightDateRecord(new RecordKey(patient.getName(), LocalDate.now()), 0);
            modelAndView.addObject("weightRecordToday", weightRecord);
        }
        return modelAndView;
    }

    @GetMapping("/weight-history")
    @Statistical
    public ModelAndView getWeightHistory(){
        ModelAndView modelAndView = new ModelAndView("weight/weight-history");
        modelAndView.addObject("previousUrl", "/weight");

        User user = getUser();
        List<Tbl_weight> weightRecord;

        if(user.getType() == User.Type.PATIENT){
            weightRecord = weightService.getWeightRecord(user.getName(), 0);
            modelAndView.addObject("weightRecord", weightRecord);
        }
        else if(user.getType() == User.Type.CAREGIVER){
            User patient  = getCaretaker();
            weightRecord = weightService.getWeightRecord(patient.getName(), 0);
            modelAndView.addObject("weightRecord", weightRecord);
        }

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
