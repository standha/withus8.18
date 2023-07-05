
package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_blood_pressure_pulse;
import withus.entity.RecordKey;
import withus.entity.Tbl_natrium_record;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodPressureRepository extends JpaRepository<Tbl_blood_pressure_pulse, RecordKey> {
    @Transactional(readOnly = true)
    List<Tbl_blood_pressure_pulse> findByPk_IdAndContractionGreaterThanAndPressureGreaterThanAndRelaxationGreaterThan(String username, Integer contraction, Integer pressure, Integer relaxation);

    @Transactional(readOnly = true)
    Optional<Tbl_blood_pressure_pulse> findByPk(RecordKey pk);
}

