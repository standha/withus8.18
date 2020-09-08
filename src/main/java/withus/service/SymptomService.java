package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_symptom_log;
import withus.repository.SymptomRepository;

import java.util.List;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    @Autowired
    public SymptomService(SymptomRepository symptomRepository){
        this.symptomRepository = symptomRepository;
    }

    @NonNull
    public List<Tbl_symptom_log> getSymptomAllRecord(RecordKey pk, Integer todaySymptom){
        return symptomRepository.findByPkAndAndTodaySymptomGreaterThan(pk ,todaySymptom);
    }
    @NonNull
    public Tbl_symptom_log upsertSymptomRecord(Tbl_symptom_log Tbl_symptom_log){
        Tbl_symptom_log saved = symptomRepository.save(Tbl_symptom_log);
        return saved;
    }
}
