package withus.service;

import com.sun.istack.Nullable;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_weight;
import withus.entity.User;
import withus.repository.WeightRecordRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeightService {
    private final WeightRecordRepository weightRecordRepository;

    @Autowired
    public WeightService(WeightRecordRepository weightRecordRepository) {
        this.weightRecordRepository = weightRecordRepository;
    }

    @NonNull
    public Tbl_weight upsertWeightRecord(Tbl_weight tbl_weight) {
        Tbl_weight saved = weightRecordRepository.save(tbl_weight);
        return saved;
    }

    @NonNull
    public List<Tbl_weight> getWeightRecord(String id, float weight) {
        return weightRecordRepository.findByPk_IdAndWeightGreaterThan(id, weight);
    }

    @Nullable
    public Tbl_weight getTodayWeight(RecordKey pk) {
        return weightRecordRepository.findByPk(pk).orElse(null);
    }

}
