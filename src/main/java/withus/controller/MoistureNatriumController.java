package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.MoistureNatriumService;
import withus.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MoistureNatriumController extends BaseController {
    private final MoistureNatriumService moistureNatriumService;
    private final CountService countService;

    @Autowired
    public MoistureNatriumController(AuthenticationFacade authenticationFacade, UserService userService, MoistureNatriumService moistureNatriumService, CountService countService) {
        super(userService, authenticationFacade);
        this.moistureNatriumService = moistureNatriumService;
        this.countService = countService;
    }

    @GetMapping("/moistureNatrium")
    public ModelAndView getMoistureNatrium() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moistureNatrium");
        modelAndView.addObject("previousUrl", "/center");

        User user = getUser();
        modelAndView.addObject("user", user);

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());

        return modelAndView;
    }

    @GetMapping("/moisture")
    public ModelAndView getMoisture() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture");

        if (moistureNatriumService.getMoisture(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("intake", 0);
            modelAndView.addObject("intakeMinus", 0);
            modelAndView.addObject("intakePlus", 0);
        } else {
            Tbl_mositrue_record moisture = moistureNatriumService.getMoisture(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("intake", moisture.getIntake());
            modelAndView.addObject("intakeMinus", moisture.getIntake());
            modelAndView.addObject("intakePlus", moisture.getIntake());
        }

        User user = getUser();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("previousUrl", "moistureNatrium");

        return modelAndView;
    }

    @GetMapping("/moisture-all-history")
    public ModelAndView getMoistureAll() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/moisture-all-history");
        User user = getUser();

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());

        Integer moistureWeek = 0;
        List<Tbl_mositrue_record> moistureAllHistory;
        moistureAllHistory = moistureNatriumService.getMoistureRecord(getConnectId(), 0);

        moistureWeek = avgWeek();
        modelAndView.addObject("moisture", moistureAllHistory);
        modelAndView.addObject("moistureWeek", moistureWeek);
        modelAndView.addObject("previousUrl", "moistureNatrium");
        modelAndView.addObject("week", user.getWeek());

        return modelAndView;
    }

    @GetMapping("/natrium")
    public ModelAndView getNatrium() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/natrium");
        if (moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("morning", 4);
            modelAndView.addObject("lunch", 4);
            modelAndView.addObject("dinner", 4);
            modelAndView.addObject("previousUrl", "moistureNatrium");
        } else {
            Tbl_natrium_record natrium = moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("morning", natrium.getMorning());
            modelAndView.addObject("lunch", natrium.getLunch());
            modelAndView.addObject("dinner", natrium.getDinner());
            modelAndView.addObject("previousUrl", "moistureNatrium");
        }

        User user = getUser();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        } else if(user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count mainCount = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            Tbl_caregiver_detail_button_count detailCount = countService.getCaregiverDetailCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("mainCount", mainCount);
            modelAndView.addObject("detailCount", detailCount);
        }

        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("type", user.getType());

        return modelAndView;
    }

    @GetMapping("/natrium-all-history")
    public ModelAndView getSymptomAll() {
        ModelAndView modelAndView = new ModelAndView("moistureNatrium/natrium-history");
        User user = getUser();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());

        LocalDate today = LocalDate.now();
        int lowCount = 0;
        int norCount = 0;
        int highCount = 0;
        List<Tbl_natrium_record> natriums = new ArrayList<>();
        List<Tbl_natrium_record> allnatriums = new ArrayList<>();

        allnatriums = moistureNatriumService.getNatriumAllRecord(getConnectId());

        for (int i = 1; i <= 7; i++) {
            if (moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i)))) != null) {
                Tbl_natrium_record sun = moistureNatriumService.getNatriumTodayRecord(new RecordKey(getConnectId(), today.with(DayOfWeek.of(i))));
                natriums.add(sun);
            }
        }

        for (Tbl_natrium_record natrium : natriums) {
            switch (natrium.getMorning()) {
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

            switch (natrium.getLunch()) {
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

            switch (natrium.getDinner()) {
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

        modelAndView.addObject("low", lowCount);
        modelAndView.addObject("normal", norCount);
        modelAndView.addObject("high", highCount);
        modelAndView.addObject("natrium", allnatriums);
        modelAndView.addObject("previousUrl", "/natrium");
        modelAndView.addObject("week", user.getWeek());
        return modelAndView;
    }

    @PostMapping("/natrium")
    @ResponseBody
    public Result<Tbl_natrium_record> postNatrium(@RequestBody Tbl_natrium_record tbl_natrium_record) {
        User user = getUser();
        tbl_natrium_record.setPk(new RecordKey(user.getUsername(), LocalDate.now()));
        tbl_natrium_record.setWeek(user.getWeek());
        Result.Code code;
        Tbl_natrium_record saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = moistureNatriumService.upsertNatriumRecord(tbl_natrium_record);
                code = Result.Code.OK;
            } else if (user.getWeek() == 25) {
                throw new IllegalStateException("25 Weeks User try input data [warn]");
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_natrium_record>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @PutMapping(value = "/moisture-history", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_mositrue_record> getMoisture(@RequestBody Tbl_mositrue_record tbl_mositrue_record) {
        User user = getUser();
        tbl_mositrue_record.setPk(new RecordKey(user.getUsername(), LocalDate.now()));
        tbl_mositrue_record.setWeek(user.getWeek());
        Result.Code code;
        Tbl_mositrue_record saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = moistureNatriumService.upsertMoistureRecord(tbl_mositrue_record);
                code = Result.Code.OK;
            } else if (user.getWeek() == 25) {
                throw new IllegalStateException("25 Weeks User try input data [warn]");
            } else {
                throw new IllegalStateException("Caregiver try input data [warn]");
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage(), exception);
            code = Result.Code.ERROR_DATABASE;
        }

        return Result.<Tbl_mositrue_record>builder()
                .code(code)
                .data(saved)
                .build();
    }

    @GetMapping("/diet")
    public String diet(Model model) {
        User user = getUser();
        User.Type type = user.getType();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count mainCount = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            Tbl_patient_detail_button_count detailCount = countService.getPatientDetailCount(new ProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("mainCount", mainCount);
            model.addAttribute("detailCount", detailCount);
        }

        model.addAttribute("user", user);
        model.addAttribute("type", type);

        return "moistureNatrium/diet/main";
    }

    public Integer avgWeek() {
        Integer avg = 0;
        int count = 0;
        LocalDate now = LocalDate.now();

        for (int i = 1; i < 8; i++) {
            int intake = moistureNatriumService.getMoistureDayRecord(new RecordKey(getConnectId(), now.with(DayOfWeek.of(i))));
            if (intake != 0) {
                avg = avg + intake;
                count++;
            }
        }

        if (count == 0) {
            return 0;
        } else {
            return avg * 200 / count;
        }
    }
}