package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.CaregiverProgressKey;
import withus.entity.ProgressKey;
import withus.entity.Tbl_caregiver_duration_time;
import withus.entity.Tbl_patient_duration_time;
import withus.repository.CaregiverDurationTimeRepository;
import withus.repository.PatientDurationTimeRepository;

import java.util.List;

@Service
public class DurationTimeService {

    private final PatientDurationTimeRepository patientDurationTimeRepository;
    private final CaregiverDurationTimeRepository caregiverDurationTimeRepository;

    @Autowired
    public DurationTimeService(PatientDurationTimeRepository patientDurationTimeRepository, CaregiverDurationTimeRepository caregiverDurationTimeRepository) {
        this.patientDurationTimeRepository = patientDurationTimeRepository;
        this.caregiverDurationTimeRepository = caregiverDurationTimeRepository;
    }
    @Nullable
    public List<Tbl_caregiver_duration_time> getCaregiverDurationTimeList(CaregiverProgressKey pk){
        return caregiverDurationTimeRepository.findAllByKey(pk);
    }

    @Nullable
    public Tbl_caregiver_duration_time getCaregiverDurationTime(CaregiverProgressKey pk){
        return caregiverDurationTimeRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_patient_duration_time getPatientDurationTime(ProgressKey pk){
        return patientDurationTimeRepository.findByKey(pk).orElse(null);
    }
}
