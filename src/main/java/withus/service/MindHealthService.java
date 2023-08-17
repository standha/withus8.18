package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_mindHealth_record;
import withus.repository.MindHealthRepository;
import withus.repository.OutPatientVisitAlarmRepository;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class MindHealthService {
    private final MindHealthRepository mindHealthRepository;

    @Autowired
    public MindHealthService(MindHealthRepository mindHealthRepository){
    this.mindHealthRepository = mindHealthRepository;
    }
    @Nullable
    public Tbl_mindHealth_record upsertMindhealth(Tbl_mindHealth_record tbl_mindHealth_record) {
        Tbl_mindHealth_record saved = mindHealthRepository.save(tbl_mindHealth_record);
        return saved;
    }
@Nullable
    public Tbl_mindHealth_record upsertmindText(Tbl_mindHealth_record tbl_mindHealth_record) {
    RecordKey pk = tbl_mindHealth_record.getPk();
    Tbl_mindHealth_record existingRecord = mindHealthRepository.findByPk(pk).orElse(null);
        if (existingRecord != null) {
            existingRecord.setText(tbl_mindHealth_record.getText()); // 기존 기록이 있는 경우 텍스트 업데이트
            return mindHealthRepository.save(existingRecord);
        } else {
            return mindHealthRepository.save(tbl_mindHealth_record); // 기존 기록이 없는 경우 새로운 엔티티 저장
        }
    }



     @Nullable
    public Tbl_mindHealth_record getmindHealth(RecordKey pk) {
        return mindHealthRepository.findByPk(pk).orElse(null);
     }

    @Nullable
    public List<Tbl_mindHealth_record> getAllMindHealthRecord(String id) {
        return mindHealthRepository.findByPk_Id(id);
    }

}
