package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_symptom_log;
import withus.repository.SymptomRepository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class SymptomService {
    private final SymptomRepository symptomRepository;

    @Autowired
    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }


    @Nullable
    public List<Tbl_symptom_log> getSymptomRecord(String id, Integer todaySymptom) {
        return symptomRepository.findByPk_IdAndTodaysymptomGreaterThan(id, todaySymptom);
    }

    @NonNull
    public Tbl_symptom_log upsertSymptomRecord(Tbl_symptom_log Tbl_symptom_log) {
        Tbl_symptom_log saved = symptomRepository.save(Tbl_symptom_log);
        return saved;
    }

    @Nullable
    public Tbl_symptom_log getSymptom(RecordKey pk) {
        return symptomRepository.findByPk(pk).orElse(null);
    }
}
