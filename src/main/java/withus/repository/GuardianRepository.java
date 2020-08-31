package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.Tbl_guardian;
import withus.entity.Tbl_guardian;
import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Tbl_guardian, Integer> {
    @Transactional(readOnly = true)
    Optional<Tbl_guardian> findByGuardianIdAndGpassword(String guardianId, String gpassword);
    Optional<Tbl_guardian> findByGtell(String gtell);
    Optional<Tbl_guardian> findByGuardianId(String guardianId);
}
