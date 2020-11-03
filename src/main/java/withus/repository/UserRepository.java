package withus.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.entity.User;

import javax.persistence.SqlResultSetMapping;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Transactional(readOnly = false)
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
    List<User> findByTypeAndWeekLessThanAndLevelLessThan(User.Type type, int limit_week, int limit_level);

    @Transactional(readOnly = true)
    @Nullable
    List<User> findByAppTokenIsNotNullAndType(User.Type type);

    @Transactional(readOnly = true)
    @Nullable
    @Query(value = "select u.name as patientName ,u.id as patientId, u.password as patientPassword, u.birthdate as patientBirthdate," +
            " u.gender as patientGender, u.contact as patientContact, c.name as caregiverName," +
            "c.id as caregiverId, c.password as caregiverPassword, c.contact as caregiverContact, u.user_record_date as userRecordDate , code as currentCode" +
            " from user as u left join user as c on c.contact = u.caregiver_contact " +
            "left join(select any_value(t.entry_code) as 'code' , any_value(t.date_time), t.user_id from" +
            "(select wwh.user_id, wwh.date_time, wwh.entry_code" +
            " from wwithus_entry_history as wwh" +
            " where(wwh.user_id, wwh.date_time)in(" +
            "select wh.user_id,max(wh.date_time)as dt" +
            " from wwithus_entry_history as wh group by wh.user_id)" +
            "order by wwh.date_time desc" +
            ")t " +
            "group by t.user_id)t on(u.id = t.user_id)" +
            "where u.type = 'PATIENT'" +
            " order by u.registration_date_time asc;", nativeQuery = true)
    ArrayList<String> findByAll();

}
