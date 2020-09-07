package withus.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;
import withus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	@Transactional(readOnly = true)
	Optional<User> findByUserId(String userId);

	@Transactional(readOnly = true)
	Optional<User> findByCaregiverUserId(String caregiverUserId);

	@Transactional(readOnly = true)
	Optional<User> findByUserIdAndPassword(String id, String password);

	@Transactional(readOnly = true)
	Optional<User> findByContact(String contact);

//	@Query(value= "select * from withus.User where id = 'dariouse'", nativeQuery=true)
	@Query(value= "select * from withus.User", nativeQuery=true)
	public String[] getAllPatient();
}
