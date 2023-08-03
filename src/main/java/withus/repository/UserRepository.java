package withus.repository;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import withus.dto.wwithus.*;
import withus.entity.User;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Transactional(readOnly = false)
    Optional<User> findByUserId(String userId);

    @Transactional(readOnly = true)
    Optional<User> findByCaregiverUserId(String caregiverUserId);

    @Transactional(readOnly = true)
    Optional<User> findByUserIdAndPassword(String id, String password);

   /* @Transactional(readOnly = true)
    Optional<User> findByHeight(Float height);*/

    @Transactional(readOnly = true)
    Optional<User> findTopByTempContact(String tempContact);

    @Transactional(readOnly = true)
    Optional<User> findByContact(String contact);

    @Transactional(readOnly = true)
    Optional<User> findTopByContact(String contact);

    @Transactional(readOnly = true)
    Optional<User> findTopByCaregiverContact(String caregiverContact);

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
    List<User> findAllByOrderByLevelDesc();

    @Transactional(readOnly = true)
    List<User> findByTypeOrderByLevelDesc(User.Type type);

    @Transactional(readOnly = true)
    @Nullable
    @Query(value = "select u.name as patientName ,u.id as patientId, u.password as patientPassword, u.birthdate as patientBirthdate," +
            " u.gender as patientGender, u.contact as patientContact, c.name as caregiverName, c.gender as caregiverGender, " +
            "c.id as caregiverId, c.password as caregiverPassword, c.contact as caregiverContact, u.user_record_date as userRecordDate , code as currentCode, " +
            "u.height as patientHeight, c.relative as caregiverRelative" +
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


    @Transactional(readOnly = true)
    @Nullable
    @Query(value = "select u.name as caregiverName ,u.id as caregiverId, u.password as caregiverPassword, u.birthdate as caregiverBirthdate," +
            " u.gender as caregiverGender, u.contact as caregiverContact, c.name as patientName, c.gender as patientrGender, " +
            "c.id as patientrId, c.password as patientPassword, c.contact as patientContact, u.user_record_date as userRecordDate , code as currentCode, " +
            "u.height as caregiverHeight, u.relative as caregiverRelative" +
            " from user as u left join user as c on u.contact = c.caregiver_contact " +
            "left join(select any_value(t.entry_code) as 'code' , any_value(t.date_time), t.user_id from" +
            "(select wwh.user_id, wwh.date_time, wwh.entry_code" +
            " from wwithus_entry_history as wwh" +
            " where(wwh.user_id, wwh.date_time)in(" +
            "select wh.user_id,max(wh.date_time)as dt" +
            " from wwithus_entry_history as wh group by wh.user_id)" +
            "order by wwh.date_time desc" +
            ")t " +
            "group by t.user_id)t on(u.id = t.user_id)" +
            "where u.type = 'CAREGIVER'" +
            " order by u.registration_date_time asc;", nativeQuery = true)
    ArrayList<String> findByAllCaregiver();

    @Transactional(readOnly = true)
    @Nonnull
    @Query(value = "SELECT ageGroup, type, COUNT(*) - 1 AS total " +
            "FROM (select CASE WHEN TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) >= 0 AND TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) < 50 THEN '49세이하' " +
            "WHEN TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) >= 50 AND TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) < 60 THEN '50세' " +
            "WHEN TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) >= 60 AND TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) < 70 THEN '60세' " +
            "ELSE '70세이상' " +
            "END AS ageGroup, type " +
            "from user UNION ALL " +
            "SELECT '49세이하' as ageGroup, 'CAREGIVER' as type UNION ALL " +
            "SELECT '50세' as ageGroup, 'CAREGIVER' as type UNION ALL " +
            "SELECT '60세' as ageGroup, 'CAREGIVER' as type UNION ALL " +
            "SELECT '70세이상' as ageGroup, 'CAREGIVER' as type UNION ALL " +
            "SELECT '49세이하' as ageGroup, 'PATIENT' as type UNION ALL " +
            "SELECT '50세' as ageGroup, 'PATIENT' as type UNION ALL " +
            "SELECT '60세' as ageGroup, 'PATIENT' as type UNION ALL " +
            "SELECT '70세이상' as ageGroup, 'PATIENT' as type) AS subquery " +
            "where NOT type ='ADMINISTRATOR' " +
            "group by ageGroup, type " +
            "order by ageGroup asc;", nativeQuery = true)
    List<UserAgeCountDTO> findUserAgeCount();

    @Transactional(readOnly = true)
    @Nonnull
    @Query(value="SELECT relative, COUNT(*) - 1 as relativeCount " +
            "FROM ( " +
            "SELECT relative FROM user " +
            "WHERE type = 'CAREGIVER' " +
            "UNION ALL " +
            "SELECT 'SPOUSE' as relative UNION ALL " +
            "SELECT 'CHILD' as relative UNION ALL " +
            "SELECT 'RELATIVE' as relative UNION ALL " +
            "SELECT 'ETC' as relative " +
            ") AS subquery " +
            "WHERE relative <> 'null' " +
            "GROUP BY relative " +
            "order by relative;",nativeQuery = true)
    List<UserRelativeCountDTO> findUserRelativeCount();

    @Transactional(readOnly = true)
    @Nonnull
    @Query(value = "SELECT type, gender, COUNT(*) - 1 AS total " +
            "FROM (select type, gender from user UNION ALL " +
            "SELECT 'CAREGIVER' as type, 'MALE' as gender UNION ALL " +
            "SELECT 'CAREGIVER' as type, 'FEMALE' as gender UNION ALL " +
            "SELECT 'PATIENT' as type, 'MALE' as gender UNION ALL " +
            "SELECT 'PATIENT' as type, 'FEMALE' as gender) AS subquery " +
            "WHERE NOT type = 'ADMINISTRATOR' " +
            "GROUP BY gender, type " +
            "ORDER BY type asc, gender desc;", nativeQuery = true)
    List<UserGenderCountDTO> findUserGenderCount();

    @Transactional(readOnly = true)
    @Nonnull
    @Query(value = "SELECT week, COUNT(*) AS total " +
            "FROM ( " +
            "SELECT CASE " +
            "WHEN DAYOFWEEK(registration_date_time) = 1 THEN (DATEDIFF(DATE(NOW()), DATE(registration_date_time)) + 6) DIV 7 " +
            "ELSE (DATEDIFF(DATE(NOW()), DATE(registration_date_time)) - 2 + DAYOFWEEK(registration_date_time)) DIV 7 " +
            "END AS week " +
            "FROM user " +
            "WHERE type = 'PATIENT' " +
            ") AS subquery " +
            "where week > 0 " +
            "GROUP BY week ",nativeQuery = true)
    List<UserRegisterCountDTO> findUserRegisterCount();

    @Transactional(readOnly = true)
    @Nonnull
    @Query(value = "SELECT week, COUNT(*) - 1 as total FROM ( " +
            "SELECT week FROM user " +
            "WHERE type = 'PATIENT' AND week < 25 AND week > 0 " +
            "UNION ALL " +
            "SELECT week FROM ( " +
            "SELECT (t1.num + t2.num * 10 + 1) AS week " +
            "FROM " +
            "(SELECT 0 AS num UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS t1, " +
            "(SELECT 0 AS num UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS t2 " +
            "WHERE (t1.num + t2.num * 10 + 1) BETWEEN 1 AND 24 " +
            ") AS subquery " +
            ") AS combined " +
            "where week <25 " +
            "GROUP BY week;", nativeQuery = true)
    List<UserWeekCountDTO> findUserWeekCount();


    @Nullable
    @Query(value = "SELECT t1.entry_code, DATE(t1.date_time) AS date " +
            "FROM wwithus_entry_history AS t1 " +
            "JOIN ( " +
            "SELECT DATE(t2.date_time) AS date, MAX(t2.date_time) AS max_date_time " +
            "FROM wwithus_entry_history AS t2 " +
            "WHERE t2.user_id = :userId " +
            "GROUP BY DATE(t2.date_time) " +
            ") AS t3 " +
            "ON DATE(t1.date_time) = t3.date AND t1.date_time = t3.max_date_time " +
            "WHERE t1.user_id = :userId " +
            "order by t1.entry_code;", nativeQuery = true)
    List<WwithusHistoryDTO> findWwithusHistory(@Param("userId") String userId);



}
