package withus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import withus.auth.AuthenticationFacade;
import withus.dto.Result;
import withus.entity.*;
import withus.service.CountService;
import withus.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ButtonCountController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final CountService countService;

    @Autowired
    public ButtonCountController(AuthenticationFacade authenticationFacade, UserService userService, CountService countService) {
        super(userService, authenticationFacade);
        this.countService = countService;
    }


    @PutMapping(value = "/caregiver-main-button-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_caregiver_main_button_count> getSymptomCount(@RequestBody Tbl_caregiver_main_button_count count, HttpServletRequest request) {
        User user = getUser();
        count.setKey(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
        Result.Code code;
        Tbl_caregiver_main_button_count saved = null;

        logger.info("id:{}, week:{}, goal:{}, level:{}, withusRang:{}, disease_info:{}, helper:{}, medicine:{}, blood_pressure:{}, exercise:{}, family_observation:{}, diet_management:{}," +
                        " weight:{}, mind_health:{}, alarm:{}, board:{}, info_edit:{}"
                , count.getKey().getId(), count.getKey().getWeek(), count.getGoal(), count.getLevel(),count.getWithusRang(), count.getDiseaseInfo(), count.getHelper(),
                count.getMedicine(), count.getBloodPressure(), count.getExercise(), count.getFamilyObservation(), count.getDietManagement(), count.getWeight(), count.getMindHealth(),
                count.getAlarm(), count.getBoard(), count.getInfoEdit());

        try {
            if (user.getType() == User.Type.PATIENT && user.getWeek() != 25) {
                saved = countService.caregiverMainUpsertCount(count);
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
        return Result.<Tbl_caregiver_main_button_count>builder()
                .code(code)
                .data(saved)
                .build();
    }
    @PutMapping(value = "/caregiver-sub-button-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_caregiver_sub_button_count> getSymptomCount(@RequestBody Tbl_caregiver_sub_button_count count, HttpServletRequest request) {
        User user = getUser();
        count.setKey(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
        Result.Code code;
        Tbl_caregiver_sub_button_count saved = null;

        logger.info("id:{}, week:{}, low_level:{}, middle_level:{}, high_level:{}, make_my_Goal:{}, medicine:{}, bloodPressure:{}, symptom:{}, exercise:{}, natrium_moisture:{}, weight:{}, " +
                        "mind_health:{}, mind_diary:{}, mind_score:{}, mind_management:{}, hof:{}, notice:{}, question:{}, share:{}, medicine_time:{}, out_patient_visit_time:{}"
                , count.getKey().getId(), count.getKey().getWeek(), count.getLowLevel(), count.getMiddleLevel(), count.getHighLevel(), count.getMakeMyGoal(),
                count.getMedicine(), count.getBloodPressure(), count.getSymptom(), count.getExercise(), count.getNatriumMoisture(), count.getWeight(),
                count.getMindHealth(), count.getMindDiary(), count.getMindScore(), count.getMindManagement(), count.getHof(), count.getNotice(), count.getQuestion(),
                count.getShare(), count.getMedicineTime(), count.getOutPatientVisitTime());

        try {
            if (user.getType() == User.Type.CAREGIVER && user.getWeek() != 25) {
                saved = countService.caregiverSubUpsertCount(count);
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

        return Result.<Tbl_caregiver_sub_button_count>builder()
                .code(code)
                .data(saved)
                .build();
    }
    @PutMapping(value = "/caregiver-detail-button-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result<Tbl_caregiver_detail_button_count> getSymptomCount(@RequestBody Tbl_caregiver_detail_button_count count, HttpServletRequest request) {
        User user = getUser();
        count.setKey(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
        Result.Code code;
        Tbl_caregiver_detail_button_count saved = null;

        logger.info("id:{}, week:{}, recommend_diet:{}, meditation:{}, body_activity{}, deep_breath:{}, consulting:{}, medicine_alarm:{}, blood_pressure_alarm:{}, symptom_alarm:{}, " +
                        "exercise_alarm:{}, natrium_moisture_alarm:{}, water_intake_alarm:{}, weightAlarm:{}, mind_diary_alarm:{}, mind_score_alarm:{}"
                , count.getKey().getId(), count.getKey().getWeek(), count.getRecommendDiet(), count.getMeditation(), count.getBodyActivity(), count.getDeepBreath(), count.getConsulting(),
                count.getMedicineAlarm(), count.getBloodPressureAlarm(), count.getSymptomAlarm(), count.getExerciseAlarm(), count.getNatriumMoistureAlarm(), count.getWeightAlarm(),
                count.getWeightAlarm(), count.getMindDiaryAlarm(), count.getMindScoreAlarm());

        try {
            if (user.getType() == User.Type.CAREGIVER && user.getWeek() != 25) {
                saved = countService.caregiverDetailUpsertCount(count);
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

        return Result.<Tbl_caregiver_detail_button_count>builder()
                .code(code)
                .data(saved)
                .build();
    }

}