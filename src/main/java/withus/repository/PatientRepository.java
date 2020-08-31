package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_guardian;
import withus.entity.Tbl_patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Tbl_patient, Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_patient> findByPatientId(String patientId);
    Optional<Tbl_patient> findByGcontact(String gcontact);
    Optional<Tbl_patient> findByPatientIdAndPassword(String patientId, String password);
    Optional<Tbl_patient> findByContact(String contact);
}
