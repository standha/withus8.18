package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.*;
import withus.repository.CaregiverDetailCountRepository;
import withus.repository.CaregiverMainCountRepository;
import withus.repository.CaregiverSubCountRepository;
import withus.repository.CountRepository;

import javax.annotation.Nullable;

@Service
public class CountService {

    private final CountRepository countRepository;
    private final CaregiverMainCountRepository caregiverMainCountRepository;
    private final CaregiverSubCountRepository  caregiverSubCountRepository;
    private final CaregiverDetailCountRepository caregiverDetailCountRepository;

    @Autowired
    public CountService( CountRepository countRepository, CaregiverDetailCountRepository caregiverDetailCountRepository,
                        CaregiverMainCountRepository caregiverMainCountRepository, CaregiverSubCountRepository  caregiverSubCountRepository) {
        this.countRepository = countRepository;
        this.caregiverMainCountRepository = caregiverMainCountRepository;
        this.caregiverSubCountRepository = caregiverSubCountRepository;
        this.caregiverDetailCountRepository = caregiverDetailCountRepository;
    }

    @NonNull
    public Tbl_patient_main_button_count upsertCount(Tbl_patient_main_button_count tbl_button_count) {
        Tbl_patient_main_button_count saved = countRepository.save(tbl_button_count);
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
    public Tbl_patient_main_button_count getCount(ProgressKey pk) {
        return countRepository.findByKey(pk).orElse(null);
    }
}
