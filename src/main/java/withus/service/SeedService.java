package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_caregiver_seed_day;
import withus.entity.Tbl_patient_seed_day;
import withus.repository.CaregiverSeedDayRepository;
import withus.repository.PatientSeedDayRepository;

@Service
public class SeedService {
    private final PatientSeedDayRepository patientSeedDayRepository;
    private final CaregiverSeedDayRepository caregiverSeedDayRepository;
    @Autowired
    public SeedService(PatientSeedDayRepository patientSeedDayRepository, CaregiverSeedDayRepository caregiverSeedDayRepository) {
        this.patientSeedDayRepository = patientSeedDayRepository;
        this.caregiverSeedDayRepository = caregiverSeedDayRepository;
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
