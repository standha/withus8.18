package withus.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import withus.entity.RecordKey;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_mositrue_record;
import withus.entity.Tbl_natrium_record;
import withus.repository.MoistureRecordRepository;
import withus.repository.NatriumRecordRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class MoistureNatriumService {
    private final MoistureRecordRepository moistureRecordRepository;
    private final NatriumRecordRepository natriumRecordRepository;

    @Autowired
    public MoistureNatriumService(MoistureRecordRepository moistureRecordRepository, NatriumRecordRepository natriumRecordRepository){
        this.moistureRecordRepository = moistureRecordRepository;
        this.natriumRecordRepository = natriumRecordRepository;
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
    @Nullable
    public Integer getMoistureDayRecord(RecordKey pk){
        Tbl_mositrue_record getDay = moistureRecordRepository.findByPkAndIntakeIsNotNull(pk).orElse(null);
        Integer dayIntake;
        if(getDay == null){ dayIntake = 0; }else{dayIntake = getDay.getIntake();}
        return dayIntake;
    }

    @NonNull
    public List<Tbl_mositrue_record> getMoistureAllRecord(RecordKey pk, Integer intake){
        return moistureRecordRepository.findByPkAndIntakeGreaterThan(pk ,intake);
    }

    @Nonnull
    public Tbl_natrium_record upsertNatriumRecord(Tbl_natrium_record tbl_natrium_record){
        return natriumRecordRepository.save(tbl_natrium_record);
    }

    @Nullable
    public Tbl_mositrue_record getMoisture(RecordKey pk){
        return moistureRecordRepository.findById(pk).orElse(null);
    }

    @Nullable
    public Tbl_natrium_record getNatriumTodayRecord(RecordKey pk){
        return natriumRecordRepository.findByPk(pk).orElse(null);
    }
    @Nonnull
    public List<Tbl_natrium_record> getNatriumAllRecord(String id){
        return natriumRecordRepository.findByPk_Id(id);
    }

}
