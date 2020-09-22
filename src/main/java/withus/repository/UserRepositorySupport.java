package withus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import withus.entity.QUser;
import withus.entity.User;


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
                .where(user.name.eq(name))
                .fetch();
    }
}
