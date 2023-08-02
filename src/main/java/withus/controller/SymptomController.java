package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.dto.dateDTO;
import withus.entity.*;
import withus.service.CountService;
import withus.service.SymptomService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class SymptomController extends BaseController {
    private final SymptomService symptomService;
    private final CountService countService;

    @Autowired
    public SymptomController(UserService userService, AuthenticationFacade authenticationFacade, SymptomService symptomService, CountService countService) {
        super(userService, authenticationFacade);
        this.symptomService = symptomService;
        this.countService = countService;
    }

    @GetMapping("/symptom")
    public ModelAndView getSymptom(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("Symptom/symptom");
        User user = getUser();
        User.Type typeCheck = user.getType();
        if (symptomService.getSymptom(new RecordKey(getConnectId(), LocalDate.now())) == null) {
            modelAndView.addObject("tired", 2);
            modelAndView.addObject("ankle", 2);
            modelAndView.addObject("breath", 2);
            modelAndView.addObject("cough", 2);
            modelAndView.addObject("text");
            logger.info("id:{}, url:{}, type:{}, level:{}, week:{} , Today's Symptom Not Recorded", user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek());
        } else {
            Tbl_symptom_log symptom = symptomService.getSymptom(new RecordKey(getConnectId(), LocalDate.now()));
            modelAndView.addObject("tired", symptom.getTired());
            modelAndView.addObject("ankle", symptom.getAnkle());
            modelAndView.addObject("breath", symptom.getOutofbreath());
            modelAndView.addObject("cough", symptom.getCough());
            modelAndView.addObject("text", symptom.getText());
            logger.info("id:{}, url:{}, type:{}, level:{}, week:{}, tired:{}, ankle:{}, breath:{}, cough:{},text:{}"
                    , user.getUserId(), request.getRequestURL(), user.getType(), user.getLevel(), user.getWeek(), symptom.getTired(), symptom.getAnkle(), symptom.getOutofbreath(), symptom.getCough(), symptom.getText());
        }
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }
        modelAndView.addObject("week", user.getWeek());
        modelAndView.addObject("type", typeCheck);
        modelAndView.addObject("previousUrl", "/center");

        return modelAndView;

    }

    @GetMapping("/symptom-all-history")
    public ModelAndView getSymptomAll() {
        ModelAndView modelAndView = new ModelAndView("Symptom/symptom-history");
        User user = getUser();
        List<Tbl_symptom_log> symtomHistory;
        symtomHistory = symptomService.getSymptomRecord(getConnectId(), -1);

        logger.info("id:{}, type:{}, level:{}, week:{} ,symptom_record_count:{}", user.getUserId(), user.getType(), user.getLevel(), user.getWeek(), symtomHistory.size());

        modelAndView.addObject("symptom", symtomHistory);
        modelAndView.addObject("previousUrl", "/symptom");

        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            modelAndView.addObject("count", count);
        }

        modelAndView.addObject("type", user.getType());
        modelAndView.addObject("week", user.getWeek());

        return modelAndView;
    }

    @PostMapping("/symptom")
    @ResponseBody
    public Result<Tbl_symptom_log> PostSymptom(@RequestBody Tbl_symptom_log tbl_symptom_log) {
        User user = getUser();

        tbl_symptom_log.setPk(new RecordKey(user.getUserId(), LocalDate.now()));
        tbl_symptom_log.setWeek(user.getWeek());

        logger.info("id:{}, date:{}, week:{}, tired:{}, ankle:{}, breath:{}, cough:{},text{}"
                , tbl_symptom_log.getPk().getId(), tbl_symptom_log.getPk().getDate(), tbl_symptom_log.getWeek(), tbl_symptom_log.getTired(), tbl_symptom_log.getAnkle(), tbl_symptom_log.getOutofbreath(), tbl_symptom_log.getCough(), tbl_symptom_log.getText());

        Result.Code code;
        Tbl_symptom_log saved = null;

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = symptomService.upsertSymptomRecord(tbl_symptom_log);
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

        return Result.<Tbl_symptom_log>builder()
                .code(code)
                .data(saved)
                .build();
    }

    //text 삭제
    @PostMapping("/deleteData")
    @ResponseBody
    public Result<Tbl_symptom_log> postDataSymptom(@RequestBody String date){

        Result.Code code;
        Tbl_symptom_log saved = null;
        User user = getUser();

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {

                logger.info("DTO : {}",date);
                LocalDate local = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);  //해당 날짜 받아오기
                Tbl_symptom_log findUser = symptomService.getSymptom(new RecordKey(user.getUserId(), local));
                findUser.setText(null);  //해당 날짜 열에 있는 text값을 null로 변경
                logger.info("findUser:{}, {}, {}",findUser.getPk().getId(), findUser.getPk().getDate(), findUser.getText());
                saved = symptomService.upsertSymptomRecord(findUser);
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

        return Result.<Tbl_symptom_log>builder()
                .code(code)
                .data(saved)
                .build();
    }

    //text수정
    @PostMapping(value="/modifyData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_symptom_log> postmodifySymptom(@RequestBody dateDTO date){

        Result.Code code;
        Tbl_symptom_log saved = null;
        User user = getUser();

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {

                logger.info("DTO : {}",date.getDate());
                LocalDate local = date.getDate();  //해당 날짜 받아오기
                Tbl_symptom_log findUser = symptomService.getSymptom(new RecordKey(user.getUserId(), local));
                findUser.setText(date.getText());  //해당 날짜 열에 있는 text값을 변경
                logger.info("findUser:{}, {}, {}",findUser.getPk().getId(), findUser.getPk().getDate(), findUser.getText());
                saved = symptomService.upsertSymptomRecord(findUser);
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

        return Result.<Tbl_symptom_log>builder()
                .code(code)
                .data(saved)
                .build();
    }

}