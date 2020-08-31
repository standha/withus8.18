package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_blood_pressure_pulse;

import java.util.Optional;

public interface BloodPressureRepository extends JpaRepository<Tbl_blood_pressure_pulse,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_blood_pressure_pulse> findByRegistrationCount (Integer registrationCount);
}
