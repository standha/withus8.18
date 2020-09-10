package withus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import withus.entity.Tbl_Exercise_record;
import withus.entity.Tbl_blood_pressure_pulse;
import withus.repository.BloodPressureRepository;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class BloodPressureService {
    private final BloodPressureRepository bloodPressureRepository;

    @Autowired
    public BloodPressureService(BloodPressureRepository bloodPressureRepository){
        this.bloodPressureRepository = bloodPressureRepository;
    }

    @Nonnull
    public Tbl_blood_pressure_pulse upsertBloodPressureRecord(Tbl_blood_pressure_pulse tbl_blood_pressure_pulse){
        Tbl_blood_pressure_pulse saved = bloodPressureRepository.save(tbl_blood_pressure_pulse);

        return saved;
    }

    @Nullable
    public List<Tbl_blood_pressure_pulse> getBloodAllRecord(String username, Integer contraction, Integer pressure, Integer relaxation){
        return bloodPressureRepository.findByPk_IdAndContractionGreaterThanAndPressureGreaterThanAndRelaxationGreaterThan(username, contraction, pressure, relaxation);
    }
}
