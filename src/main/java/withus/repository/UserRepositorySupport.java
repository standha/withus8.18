package withus.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import withus.dto.wwithus.HeaderInfoDTO;
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


    public List<Tuple> findMoistureWeek(String userId){
        QTbl_mositrue_record mr = QTbl_mositrue_record.tbl_mositrue_record;
        return queryFactory.select(mr.intake, mr.week)
                .from(mr)
                .where(mr.pk.id.eq(userId))
                .groupBy(mr.week)
                .fetch();
    }
    public HeaderInfoDTO findHeaderInfo(String userID){
        QUser user = QUser.user;
        QTbl_goal goal = QTbl_goal.tbl_goal;

        HeaderInfoDTO headerInfo = queryFactory.select(Projections.constructor(HeaderInfoDTO.class, user.name, user.userId, user.birthdate, goal.goal))
                .from(user)
                .leftJoin(goal).on(user.userId.eq(goal.goalId))
                .where(user.userId.eq(userID))
                .fetchFirst();
        return headerInfo;
    }
}

