package withus.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import withus.entity.QUser;
import withus.entity.QWwithusEntryHistory;
import withus.entity.User;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory){
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public List<User> findByName(String name){
        QUser user = QUser.user;
        return queryFactory.selectFrom(user)
                .where(user.name.eq(name) , user.gender.eq(User.Gender.MALE))
                .fetch();
    }
    public List<Tuple> findByUserOrderBy(){
        QUser user = QUser.user;
        QUser caregiver = QUser.user.caregiver;
        QWwithusEntryHistory history = QWwithusEntryHistory.wwithusEntryHistory;
        return queryFactory.select(user.registrationDateTime, user.name, user.userId, user.password, user.birthdate, user.gender,
                user.contact, caregiver.name , caregiver.userId, caregiver.contact, history.key.entry.code)
                .from(user)
                .join(user)
                .on(user.caregiver.contact.eq(caregiver.contact))
                .join(history)
                .on(history.key.user.userId.eq(user.userId))
                .orderBy(history.dateTime.asc()).limit(1)
                .fetch();
    }

}
