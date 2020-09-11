package withus.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_weight;
import withus.repository.WeightRecordRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeightService {
    private final WeightRecordRepository weightRecordRepository;

    @Autowired
    public WeightService(WeightRecordRepository weightRecordRepository){
        this.weightRecordRepository = weightRecordRepository;
    }

    @NonNull
    public Tbl_weight upsertWeightRecord(Tbl_weight tbl_weight){
        Tbl_weight saved = weightRecordRepository.save(tbl_weight);
        return saved;
    }

    @NonNull
    public List<Tbl_weight> getWeightRecord(String id, float weight){
        return weightRecordRepository.findByPk_IdAndWeightGreaterThan(id, weight);
    }

    @NonNull
    public Tbl_weight getWeightToday(RecordKey pk, float weight){
        return weightRecordRepository.findByPk_DateAndWeightGreaterThan(pk, weight);
    }

    @NonNull
    public List<Tbl_weight> getWeightDateRecord(RecordKey pk, float weight){
        return weightRecordRepository.findByPkAndWeightGreaterThan(pk, weight);
    }

    @NonNull
    public Tbl_weight getWeight(RecordKey pk, float weight){
        return weightRecordRepository.findByPkAndWeight(pk, weight);
    }

}
