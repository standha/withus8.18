package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_natrium_record;
import withus.service.MoistureNatriumService;
import withus.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MoistureNatriumController extends BaseController{
    private final MoistureNatriumService moistureNatriumService;

    @Autowired
    public MoistureNatriumController(AuthenticationFacade authenticationFacade, UserService userService, MoistureNatriumService moistureNatriumService){
        super(userService, authenticationFacade);
        this.moistureNatriumService = moistureNatriumService;
    }

    @GetMapping("/moistureNatrium")
    @Statistical
    public ModelAndView getMoistureNatrium() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moistureNatrium");
        String user = getUsername();
        modelAndView.addObject("previousUrl", "/home");
        return modelAndView;
    }

    @GetMapping("/moisture")
    @Statistical
    public ModelAndView getMoisture(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture");
        String username = getUsername();
        if(moistureNatriumService.getMoisture(new RecordKey(getConnectId(), LocalDate.now()))==null){
            modelAndView.addObject("intake", 0);
            modelAndView.addObject("intakeMinus", 0);
            modelAndView.addObject("intakePlus", 0);
        }else{
            Tbl_mositrue_record moisture = moistureNatriumService.getMoisture(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("intake", moisture.getIntake());
            modelAndView.addObject("intakeMinus", moisture.getIntake());
            modelAndView.addObject("intakePlus", moisture.getIntake());
        }
        modelAndView.addObject("type", getUser().getType());
        modelAndView.addObject("previousUrl","moistureNatrium");
        return modelAndView;
    }
    @GetMapping("/moisture-all-history")
    @Statistical
    public ModelAndView getMoistureAll(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture-all-history");
        String username = getUsername();
        Integer moistureWeek = 0;
        List<Tbl_mositrue_record> moistureAllHistory;
        moistureAllHistory = moistureNatriumService.getMoistureRecord(getConnectId(),0);
        moistureWeek = avgWeek();
        modelAndView.addObject("moisture",moistureAllHistory);
        modelAndView.addObject("moistureWeek",moistureWeek);
        modelAndView.addObject("previousUrl","moistureNatrium");
        return modelAndView;
    }

    @GetMapping("/natrium")
    @Statistical
    public ModelAndView getNatrium(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/natrium");
        String username = getUsername();
        if (moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), LocalDate.now()))==null) {
            modelAndView.addObject("morning",4);
            modelAndView.addObject("lunch",4);
            modelAndView.addObject("dinner",4);
            modelAndView.addObject("previousUrl","moistureNatrium");
        }
        else{
            Tbl_natrium_record natrium = moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("morning",natrium.getMorning());
            modelAndView.addObject("lunch",natrium.getLunch());
            modelAndView.addObject("dinner",natrium.getDinner());
            modelAndView.addObject("previousUrl","moistureNatrium");
        }
        modelAndView.addObject("type", getUser().getType());
        return modelAndView;
    }

    @GetMapping("/natrium-all-history")
    @Statistical
    public ModelAndView getSymptomAll(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/natrium-history");
        String username = getUsername();
        LocalDate today = LocalDate.now();
        int lowCount =0;
        int norCount =0;
        int highCount =0;
        List<Tbl_natrium_record> natriums = new ArrayList<>();
        for(int i=1; i<=7; i++){
            if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i))))!=null){
                Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i))));
                natriums.add(sun);
            }
        }
        for(Tbl_natrium_record natrium: natriums){
            switch(natrium.getMorning()){
                case 1:
                    lowCount++;
                    break;
                case 2:
                    norCount++;
                    break;
                case 3:
                    highCount++;
                    break;
                case 0:
                    break;
            }
            switch(natrium.getLunch()){
                case 1:
                    lowCount++;
                    break;
                case 2:
                    norCount++;
                    break;
                case 3:
                    highCount++;
                    break;
                case 0:
                    break;
            }
            switch(natrium.getDinner()){
                case 1:
                    lowCount++;
                    break;
                case 2:
                    norCount++;
                    break;
                case 3:
                    highCount++;
                    break;
                case 0:
                    break;
            }

        }
        modelAndView.addObject("low",lowCount);
        modelAndView.addObject("normal",norCount);
        modelAndView.addObject("high",highCount);
        modelAndView.addObject("natrium",natriums);
        modelAndView.addObject("previousUrl","/natrium")
;
        return modelAndView;
    }

    @PostMapping("/natrium")
    @ResponseBody
    public Result<Tbl_natrium_record> postNatrium(@RequestBody Tbl_natrium_record tbl_natrium_record){
        String username = getUsername();
        tbl_natrium_record.setPk(new RecordKey(username, LocalDate.now()));
        Result.Code code;
        Tbl_natrium_record seved = null;
        try{
            seved = moistureNatriumService.upsertNatriumRecord(tbl_natrium_record);
            code = Result.Code.OK;
        } catch (Exception exception){
            logger.error(exception.getLocalizedMessage(),exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_natrium_record>builder()
                .setCode(code)
                .setData(seved)
                .createResult();
    }

    @PutMapping(value = "/moisture-history",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_mositrue_record> getMoisture(@RequestBody Tbl_mositrue_record tbl_mositrue_record){
        String userId = getUsername();
        tbl_mositrue_record.setPk(new RecordKey(userId,LocalDate.now()));
        Result.Code code;
        Tbl_mositrue_record saved = null;
        try{
            saved = moistureNatriumService.upsertMoistureRecord(tbl_mositrue_record);
            code = Result.Code.OK;
        }catch (Exception exception){
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }
        return Result.<Tbl_mositrue_record>builder()
                .setCode(code)
                .setData(saved)
                .createResult();
    }
    public Integer avgWeek(){
        Integer avg = 0;
        LocalDate now = LocalDate.now();
        for(int i=1; i<8; i++){
            avg = avg+  moistureNatriumService.getMoistureDayRecord(new RecordKey(getConnectId(),now.with(DayOfWeek.of(i))));
        }
        return avg*200/7;
    }
}