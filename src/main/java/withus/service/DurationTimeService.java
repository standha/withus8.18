package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.CaregiverDurationTimeRepository;
import withus.repository.PatientDurationTimeRepository;

import javax.annotation.Nonnull;
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
    public Tbl_caregiver_duration_time getCaregiverDurationTime(CaregiverProgressKey pk){
        return caregiverDurationTimeRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_patient_duration_time getPatientDurationTime(ProgressKey pk){
        return patientDurationTimeRepository.findByKey(pk).orElse(null);
    }

    @Nonnull
    public void upsertCaregiverDurationTime(Tbl_caregiver_duration_time tbl_caregiver_duration_time) {
        caregiverDurationTimeRepository.save(tbl_caregiver_duration_time);
    }

    @Nonnull
    public void upsertPatientDurationTime(Tbl_patient_duration_time tbl_patient_duration_time) {
        patientDurationTimeRepository.save(tbl_patient_duration_time);
    }

    @Nonnull
    public void savePatientDurationTime(User user) {
        Tbl_patient_duration_time newDt =Tbl_patient_duration_time.builder()
                .key(new ProgressKey(user.getUserId(), user.getWeek()))
                .main(0)
                .goal(0)
                .level(0)
                .withusRang(0)
                .diseaseInfo(0)
                .medicine(0)
                .bloodPressure(0)
                .exercise(0)
                .symptom(0)
                .natriumMoisture(0)
                .weight(0)
                .mindHealth(0)
                .board(0)
                .alarm(0)
                .build();
        patientDurationTimeRepository.save(newDt);
    }
    public void saveCaregiverDurationTime(User user) {
        Tbl_caregiver_duration_time newDt =Tbl_caregiver_duration_time.builder()
                .key(new CaregiverProgressKey(user.getUserId(), user.getWeek()))
                .main(0)
                .goal(0)
                .level(0)
                .withusRang(0)
                .diseaseInfo(0)
                .medicine(0)
                .bloodPressure(0)
                .exercise(0)
                .familyObservation(0)
                .dietManagement(0)
                .weight(0)
                .mindHealth(0)
                .board(0)
                .alarm(0)
                .build();
        caregiverDurationTimeRepository.save(newDt);
    }
}
