package withus.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import withus.entity.*;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.IntPredicate;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }
    public List<String> findByUserIdPatient(){
        QUser patient = QUser.user;
        return queryFactory.select(patient.userId).from(patient)
                .where(patient.type.eq(User.Type.PATIENT))
                .orderBy(patient.registrationDateTime.asc())
                .fetch();
    }
    public Tuple findByOneUser(String userid){
        QUser patient = QUser.user;
        QUser caregiver = QUser.user.caregiver;
        QWwithusEntryHistory history = QWwithusEntryHistory.wwithusEntryHistory;
        return queryFactory.select(patient.userId, patient.password, patient.name
        ,caregiver.userId, caregiver.password, history.key.entry.code).from(patient)
                .leftJoin(caregiver).on(patient.caregiver.contact.eq(caregiver.contact))
                .leftJoin(history).on(patient.userId.eq(history.key.user.userId))
                .where(patient.type.eq(User.Type.PATIENT))
                .where(patient.userId.eq(userid))
                .orderBy(history.dateTime.desc())
                .fetchFirst();
    }
}

