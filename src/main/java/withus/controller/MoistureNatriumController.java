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

import java.time.LocalDate;
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