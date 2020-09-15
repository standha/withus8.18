package withus.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
            User.Type typeCheck = getUser().getType();
            switch (typeCheck){
                case PATIENT:
                    if(weightService.getTodayWeight(new RecordKey(getUsername(), LocalDate.now()))==null){
                        modelAndView.addObject("weight", "오늘 몸무게를 입력해봐요!"); //객체가 비어있어 타임리프에 null point 오류를 해결해주도록 한다. weight에 0kg을 뷰해줌
                    }else{
                        Tbl_weight weight = weightService.getTodayWeight(new RecordKey(getUsername(), LocalDate.now()));
                        modelAndView.addObject("weight", weight.getWeight());
                    }
                    break;
                case CAREGIVER:
                    if(weightService.getTodayWeight(new RecordKey(getCaretaker().getUserId(), LocalDate.now()))==null){
                        System.out.println(getCaretaker().getName());
                        System.out.println(getCaretaker().getUserId());
                        modelAndView.addObject("weight", "환자분께서 몸무게를 입력하지 않으셨습니다."); //객체가 비어있어 타임리프에 null point 오류를 해결해주도록 한다. weight에 0kg을 뷰해줌
                    }else{
                        Tbl_weight weight = weightService.getTodayWeight(new RecordKey(getCaretaker().getUserId(), LocalDate.now()));
                        modelAndView.addObject("weight", weight.getWeight());
                    }
                    break;
            }
            modelAndView.addObject("type",typeCheck);
            modelAndView.addObject("previousUrl", "/home");
            return modelAndView;
    }

    @GetMapping("/weight-all-history")
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


    @PostMapping(value = "/weight", consumes = MediaType.APPLICATION_JSON_VALUE)
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
