package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_mositrue_record;
import withus.repository.MoistureRecordRepository;
import javax.annotation.Nullable;
import java.util.List;

@Service
public class MoistureNatriumService {
    private final MoistureRecordRepository moistureRecordRepository;

    @Autowired
    public MoistureNatriumService(MoistureRecordRepository moistureRecordRepository){
        this.moistureRecordRepository = moistureRecordRepository;
    }

    @NonNull
    public Tbl_mositrue_record upsertMoistureRecord(Tbl_mositrue_record tbl_mositrue_record){
        Tbl_mositrue_record saved = moistureRecordRepository.save(tbl_mositrue_record);
        return saved;
    }

    @Nullable
    public List<Tbl_mositrue_record> getMoistureRecord(String id, Integer intake){
        return moistureRecordRepository.findByPk_IdAndIntakeGreaterThan(id, intake);
    }

    @NonNull
    public List<Tbl_mositrue_record> getMoistureAllRecord(RecordKey pk, Integer intake){
        return moistureRecordRepository.findByPkAndIntakeGreaterThan(pk ,intake);
    }

}
