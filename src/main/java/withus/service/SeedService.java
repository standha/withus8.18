package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.CaregiverSeedDayRepository;
import withus.repository.PatientSeedDayRepository;
import withus.repository.SeedWeekRepository;

@Service
public class SeedService {
    private final PatientSeedDayRepository patientSeedDayRepository;
    private final CaregiverSeedDayRepository caregiverSeedDayRepository;
    private final SeedWeekRepository seedWeekRepository;
    @Autowired
    public SeedService(PatientSeedDayRepository patientSeedDayRepository, CaregiverSeedDayRepository caregiverSeedDayRepository, SeedWeekRepository seedWeekRepository) {
        this.patientSeedDayRepository = patientSeedDayRepository;
        this.caregiverSeedDayRepository = caregiverSeedDayRepository;
        this.seedWeekRepository = seedWeekRepository;
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
