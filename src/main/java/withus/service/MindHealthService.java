package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_mindHealth_record;
import withus.repository.MindHealthRepository;
import withus.repository.OutPatientVisitAlarmRepository;

import javax.annotation.Nullable;

@Service
public class MindHealthService {
    private final MindHealthRepository mindHealthRepository;

    @Autowired
    public MindHealthService(MindHealthRepository mindHealthRepository){
    this.mindHealthRepository = mindHealthRepository;
    }

     @Nullable
    public Tbl_mindHealth_record getmindHealth(RecordKey pk) {
        return mindHealthRepository.findByPk(pk).orElse(null);
     }

}
