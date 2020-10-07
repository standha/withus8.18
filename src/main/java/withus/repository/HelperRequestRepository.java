package withus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withus.entity.Tbl_goal;
import withus.entity.Tbl_helper_request;
import withus.entity.TimeKey;

@Repository
public interface HelperRequestRepository extends JpaRepository<Tbl_helper_request, TimeKey> {
}
