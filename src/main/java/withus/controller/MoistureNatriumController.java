package withus.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.aspect.Statistical;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.MoistureNatriumService;
import withus.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
        System.out.println("UserName : "+user);
        return modelAndView;
    }
    @GetMapping("/moisture")
    @Statistical
    public ModelAndView getMoisture(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture");
        String username = getUsername();
        List<Tbl_mositrue_record> moistureHistory;
        moistureHistory = moistureNatriumService.getMoistureAllRecord(new RecordKey(username, LocalDate.now()), -1);
        modelAndView.addObject("moisture",moistureHistory);
        modelAndView.addObject("previousUrl","moistureNatrium");
        return modelAndView;
    }
    @GetMapping("/moisture-all-history")
    @Statistical
    public ModelAndView getMoistureAll(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture-all-history");
        String username = getUsername();
        List<Tbl_mositrue_record> moistureAllHistory;
        moistureAllHistory = moistureNatriumService.getMoistureRecord(username,0);
        modelAndView.addObject("moisture",moistureAllHistory);
        modelAndView.addObject("previousUrl","moistureNatrium");
        return modelAndView;
    }

    @GetMapping("/natrium")
    @Statistical
    public ModelAndView getNatrium(){
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/natrium");
        String username = getUsername();
        if (moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, LocalDate.now()))==null) {
            modelAndView.addObject("morning",4);
            modelAndView.addObject("lunch",4);
            modelAndView.addObject("dinner",4);
            modelAndView.addObject("previousUrl","moistureNatrium");
            return modelAndView;
        }
        else{
            Tbl_natrium_record natrium = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, LocalDate.now()));
            modelAndView.addObject("morning",natrium.getMorning());
            modelAndView.addObject("lunch",natrium.getLaunch());
            modelAndView.addObject("dinner",natrium.getDinner());
            modelAndView.addObject("previousUrl","moistureNatrium");
            return modelAndView;
        }
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
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.SUNDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.SUNDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.MONDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.MONDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.TUESDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.TUESDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.WEDNESDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.WEDNESDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.THURSDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.THURSDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.FRIDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.FRIDAY)));
            natriums.add(sun);
        }
        if(moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.SATURDAY)))!=null){
            Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, today.with(DayOfWeek.SATURDAY)));
            natriums.add(sun);
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
            }
            switch(natrium.getLaunch()){
                case 1:
                    lowCount++;
                    break;
                case 2:
                    norCount++;
                    break;
                case 3:
                    highCount++;
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
}