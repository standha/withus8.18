package withus.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
        Integer moistureWeek = 0;
        List<Tbl_mositrue_record> moistureAllHistory;
        moistureAllHistory = moistureNatriumService.getMoistureRecord(username,0);
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
        List<Tbl_natrium_record> natriumHistory;
        natriumHistory = moistureNatriumService.getNatriumTodayRecord(new RecordKey(username, LocalDate.now()));
        System.out.println(natriumHistory);
        modelAndView.addObject("natrium",natriumHistory);
        modelAndView.addObject("previousUrl","moistureNatrium");
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
        String username = getUsername();
        avg = moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.MONDAY))) +
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.TUESDAY)))+
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.WEDNESDAY))) +
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.THURSDAY))) +
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.FRIDAY))) +
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.SATURDAY)))+
                moistureNatriumService.getMoistureDayRecord(new RecordKey(username,now.with(DayOfWeek.SUNDAY)));
        System.out.println(avg);
        return avg*200/7;
    }
}