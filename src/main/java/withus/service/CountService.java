package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.*;

import javax.annotation.Nullable;

@Service
public class CountService {

    private final PatientMainCountRepository patientMainCountRepository;
    private final PatientSubCountRepository patientSubCountRepository;
    private final PatientDetailCountRepository patientDetailCountRepository;
    private final CaregiverMainCountRepository caregiverMainCountRepository;
    private final CaregiverSubCountRepository  caregiverSubCountRepository;
    private final CaregiverDetailCountRepository caregiverDetailCountRepository;


    @Autowired
    public CountService(PatientMainCountRepository patientMainCountRepository, PatientSubCountRepository patientSubCountRepository, PatientDetailCountRepository patientDetailCountRepository, CaregiverDetailCountRepository caregiverDetailCountRepository,
                        CaregiverMainCountRepository caregiverMainCountRepository, CaregiverSubCountRepository  caregiverSubCountRepository) {
        this.patientMainCountRepository = patientMainCountRepository;
        this.patientSubCountRepository = patientSubCountRepository;
        this.caregiverMainCountRepository = caregiverMainCountRepository;
        this.caregiverSubCountRepository = caregiverSubCountRepository;
        this.caregiverDetailCountRepository = caregiverDetailCountRepository;
        this.patientDetailCountRepository = patientDetailCountRepository;

    }

    // 환자 클릭 수 카운트
    @NonNull
    public Tbl_patient_main_button_count patientMainUpsertCount(Tbl_patient_main_button_count tbl_button_count) {
        Tbl_patient_main_button_count saved = patientMainCountRepository.save(tbl_button_count);
        return saved;
    }
    @NonNull
    public Tbl_patient_sub_button_count patientSubUpsertCount(Tbl_patient_sub_button_count tbl_patient_sub_button_count){
        Tbl_patient_sub_button_count saved = patientSubCountRepository.save(tbl_patient_sub_button_count);
        return saved;
    }
    @NonNull
    public Tbl_patient_detail_button_count patientDetailUpsertCount(Tbl_patient_detail_button_count tbl_patient_detail_button_count){
        Tbl_patient_detail_button_count saved = patientDetailCountRepository.save(tbl_patient_detail_button_count);
        return saved;
    }
    //보호자 클릭 수 카운트
    @NonNull
    public Tbl_caregiver_main_button_count caregiverMainUpsertCount(Tbl_caregiver_main_button_count tbl_caregiver_main_button_count) {
        Tbl_caregiver_main_button_count saved = caregiverMainCountRepository.save(tbl_caregiver_main_button_count);
        return saved;
    }
    @NonNull
    public Tbl_caregiver_sub_button_count caregiverSubUpsertCount(Tbl_caregiver_sub_button_count tbl_caregiver_sub_button_count) {
        Tbl_caregiver_sub_button_count saved = caregiverSubCountRepository.save(tbl_caregiver_sub_button_count);
        return saved;
    }
    @NonNull
    public Tbl_caregiver_detail_button_count caregiverDetailUpsertCount(Tbl_caregiver_detail_button_count tbl_caregiver_detail_button_count) {
        Tbl_caregiver_detail_button_count saved = caregiverDetailCountRepository.save(tbl_caregiver_detail_button_count);
        return saved;
    }
    @Nullable
    public Tbl_patient_main_button_count getPatientMainCount(ProgressKey pk) {
        return patientMainCountRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_patient_sub_button_count getPatientSubCount(ProgressKey pk) {
        return patientSubCountRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_patient_detail_button_count getPatientDetailCount(ProgressKey pk){
        return patientDetailCountRepository.findByKey(pk).orElse(null);
    }

    @Nullable
    public Tbl_caregiver_main_button_count getCaregiverMainCount(CaregiverProgressKey pk){
        return caregiverMainCountRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_caregiver_sub_button_count getCaregiverSubCount(CaregiverProgressKey pk){
        return caregiverSubCountRepository.findByKey(pk).orElse(null);
    }
    @Nullable
    public Tbl_caregiver_detail_button_count getCaregiverDetailCount(CaregiverProgressKey pk){
        return caregiverDetailCountRepository.findByKey(pk).orElse(null);
    }
}
