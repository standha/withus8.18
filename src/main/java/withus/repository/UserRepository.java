package withus.repository;

import java.util.List;
import java.util.Optional;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Transactional(readOnly = true)
	Optional<User> findByUserId(String userId);

	@Transactional(readOnly = true)
	Optional<User> findByCaregiverUserId(String caregiverUserId);


	@Transactional(readOnly = true)
	Optional<User> findByUserIdAndPassword(String id, String password);

	@Transactional(readOnly = true)
	Optional<User> findByContact(String contact);

	@Transactional(readOnly = true)
	@Nullable
	List<User> findByAppTokenIsNotNull();

	@Transactional(readOnly = true)
	@Nullable
	List<User> findByType(User.Type type);

	@Transactional(readOnly = true)
	@Nullable
	List<User> findByAppTokenIsNotNullAndType(User.Type type);
}
