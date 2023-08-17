package withus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.*;

@Service
public class SeedService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final PatientSeedDayRepository patientSeedDayRepository;
    private final CaregiverSeedDayRepository caregiverSeedDayRepository;
    private final SeedWeekRepository seedWeekRepository;
    private final WwithusEntryHistoryRepository wwithusEntryHistoryRepository;
    private final UserRepositorySupport userRepositorySupport;
    @Autowired
    public SeedService(PatientSeedDayRepository patientSeedDayRepository, CaregiverSeedDayRepository caregiverSeedDayRepository, SeedWeekRepository seedWeekRepository,
                       WwithusEntryHistoryRepository wwithusEntryHistoryRepository,UserRepositorySupport userRepositorySupport) {
        this.patientSeedDayRepository = patientSeedDayRepository;
        this.caregiverSeedDayRepository = caregiverSeedDayRepository;
        this.seedWeekRepository = seedWeekRepository;
        this.wwithusEntryHistoryRepository = wwithusEntryHistoryRepository;
        this.userRepositorySupport = userRepositorySupport;
    }


    @Nullable
    public Tbl_seed_week getSeedWeek(WeekUserKey key){
        return seedWeekRepository.findByKey(key).orElse(null);
    }
    @Nullable
    public Tbl_seed_week upsertSeedWeek(Tbl_seed_week seedWeek){
        return seedWeekRepository.save(seedWeek);
    }

    @Nullable
    public Tbl_patient_seed_day getPatientSeed(RecordKey pk){
        return patientSeedDayRepository.findByPk(pk).orElse(null);
    }


    @Nullable
    public Integer getSeedSum(String userId, User.Type type) {
        Integer week = userRepositorySupport.findWeekSeedSum(userId);
        Integer day = 0;
        if(type == User.Type.PATIENT){
            day = userRepositorySupport.findPatientDaySeedSum(userId);
        } else if(type == User.Type.CAREGIVER){
            day = userRepositorySupport.findCaregiverDaySeedSum(userId);
        }
        Long entry = wwithusEntryHistoryRepository.findWwithusSeedSum(userId);

        logger.info("seed userId : {}, week : {}, day : {}, entry : {}", userId, week,day, entry);
        return week + day + entry.intValue();
    }
    @Nullable
    public void upsertPatientSeed(Tbl_patient_seed_day tblPatientSeedDay){
        patientSeedDayRepository.save(tblPatientSeedDay);
    }

    @Nullable
    public Tbl_caregiver_seed_day getCaregiverSeed(RecordKey pk){
        return caregiverSeedDayRepository.findByPk(pk).orElse(null);
    }

    @Nullable
    public void upsertCaregiverSeed(Tbl_caregiver_seed_day tblCaregiverSeedDay){
        caregiverSeedDayRepository.save(tblCaregiverSeedDay);
    }
}
