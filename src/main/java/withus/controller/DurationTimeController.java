package withus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import withus.auth.AuthenticationFacade;
import withus.entity.*;
import withus.service.DurationTimeService;
import withus.service.UserService;

import java.util.Objects;

@RestController
public class DurationTimeController extends BaseController{
    private final DurationTimeService durationTimeService;

    @Autowired
    public DurationTimeController(UserService userService, AuthenticationFacade authenticationFacade, DurationTimeService durationTimeService) {
        super(userService, authenticationFacade);
        this.durationTimeService = durationTimeService;
    }

    @PostMapping(value = "/duration_time/{page}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> AddDurationTime(@RequestBody String data, @PathVariable("page") PageType pageType) {
        User user = getUser();
        Integer count = Integer.parseInt(data);
        logger.info("page : {}, count : {}", pageType, count);
        try {
            if (user.getType() == User.Type.PATIENT && count > 0) {
                Tbl_patient_duration_time dt = durationTimeService.getPatientDurationTime(new ProgressKey(user.getUserId(), user.getWeek()));
                if (dt != null) {
                    pageType.processDurationTime(dt, count);
                    durationTimeService.upsertPatientDurationTime(dt);
                } else {
                    durationTimeService.savePatientDurationTime(user);
                    dt = durationTimeService.getPatientDurationTime(new ProgressKey(user.getUserId(), user.getWeek()));
                    pageType.processDurationTime(dt, count);
                    durationTimeService.upsertPatientDurationTime(dt);
                }
            } else if (user.getType() == User.Type.CAREGIVER && count > 0) {
                Tbl_caregiver_duration_time dt = durationTimeService.getCaregiverDurationTime(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
                if (dt != null) {
                    pageType.processCaregiverDurationTime(dt, count);
                    durationTimeService.upsertCaregiverDurationTime(dt);
                } else {
                    durationTimeService.saveCaregiverDurationTime(user);
                    dt = durationTimeService.getCaregiverDurationTime(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
                    pageType.processCaregiverDurationTime(dt, count);
                    durationTimeService.upsertCaregiverDurationTime(dt);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.ok("err");
        }
        return ResponseEntity.ok("Success");
    }

    public enum PageType {
        MAIN {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setMain(dt.getMain() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setMain(dt.getMain() + count);
            }

        },
        GOAL {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setGoal(dt.getGoal() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setGoal(dt.getGoal() + count);
            }

        },
        LEVEL {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setLevel(dt.getLevel() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setLevel(dt.getLevel() + count);
            }
        },
        DISEASE_INFO {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setDiseaseInfo(dt.getDiseaseInfo() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setDiseaseInfo(dt.getDiseaseInfo() + count);
            }
        },
        WITHUS_RANG {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setWithusRang(dt.getWithusRang() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setWithusRang(dt.getWithusRang() + count);
            }
        },
        MEDICINE {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setMedicine(dt.getMedicine() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setMedicine(dt.getMedicine() + count);
            }
        },
        BLOOD_PRESSURE{
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setBloodPressure(dt.getBloodPressure() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setBloodPressure(dt.getBloodPressure() + count);
            }
        },
        EXERCISE {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setExercise(dt.getExercise() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setExercise(dt.getExercise() + count);
            }
        },
        SYMPTOM {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setSymptom(dt.getSymptom() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
            }
        },
        NATRIUM_MOISTURE {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setNatriumMoisture(dt.getNatriumMoisture() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
            }
        },
        WEIGHT {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setWeight(dt.getWeight() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setWeight(dt.getWeight() + count);
            }
        },
        MIND_HEALTH {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setMindHealth(dt.getMindHealth() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setMindHealth(dt.getMindHealth() + count);
            }
        },
        BOARD {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setBoard(dt.getBoard() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setBoard(dt.getBoard() + count);
            }
        },
        ALARM {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
                dt.setAlarm(dt.getAlarm() + count);
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setAlarm(dt.getAlarm() + count);
            }
        },
        FAMILY_OBSERVATION {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setFamilyObservation(dt.getFamilyObservation() + count);
            }
        },
        DIET_MANAGEMENT {
            @Override
            public void processDurationTime(Tbl_patient_duration_time dt, int count) {
            }
            @Override
            public void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count) {
                dt.setDietManagement(dt.getDietManagement() + count);
            }
        }
        ;

        public abstract void processDurationTime(Tbl_patient_duration_time dt, int count);

        public abstract void processCaregiverDurationTime(Tbl_caregiver_duration_time dt, int count);
        private DurationTimeService durationTimeService;

        public void setDurationTimeService(DurationTimeService durationTimeService) {
            this.durationTimeService = durationTimeService;
        }
        PageType() {
            setDurationTimeService(durationTimeService);
        }
    }
}
