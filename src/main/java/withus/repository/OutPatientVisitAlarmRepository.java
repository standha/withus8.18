package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_medication_alarm;
import withus.entity.Tbl_outpatient_visit_alarm;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface OutPatientVisitAlarmRepository extends JpaRepository<Tbl_outpatient_visit_alarm,String> {
    @Transactional(readOnly = true)
    Optional<Tbl_outpatient_visit_alarm> findById (String id);

    @Transactional(readOnly = true)
    @Nonnull
    List<Tbl_outpatient_visit_alarm>findByVisitAlarmIsTrue();
}
