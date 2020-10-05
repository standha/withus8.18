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


    public List<Tuple> findMoistureWeek(String userId){
        QTbl_mositrue_record mr = QTbl_mositrue_record.tbl_mositrue_record;
        return queryFactory.select(mr.intake, mr.week)
                .from(mr)
                .where(mr.pk.id.eq(userId))
                .groupBy(mr.week)
                .fetch();
    }
}

