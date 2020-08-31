package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_outpatient_visit_alarm;

import java.util.Optional;

public interface OutPatientVisitAlarmRepositroy extends JpaRepository<Tbl_outpatient_visit_alarm,Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_outpatient_visit_alarm> findByRegistrationCount (Integer registrationCount);
}
