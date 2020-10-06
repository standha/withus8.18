package withus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import withus.dto.HeaderInfoDTO;
import withus.dto.MoistureAvgDTO;
import withus.dto.PillSumDTO;
import withus.entity.*;

import java.util.List;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;

    }

    public List<MoistureAvgDTO> findMoistureWeek(String userId){
        QTbl_mositrue_record mr = QTbl_mositrue_record.tbl_mositrue_record;

        List<MoistureAvgDTO> moistureAvg = queryFactory.select(Projections.constructor(MoistureAvgDTO.class, mr.week, mr.intake.sum()))
                .from(mr)
                .groupBy(mr.week)
                .orderBy(mr.week.asc())
                .where(mr.pk.id.eq(userId))
                .fetch();
        return moistureAvg;
    }
    public List<Tbl_mositrue_record> findMoistureAsc(String userId){
        QTbl_mositrue_record mr =QTbl_mositrue_record.tbl_mositrue_record;
        List<Tbl_mositrue_record> moistureAsc = queryFactory.selectFrom(mr)
                .where(mr.pk.id.eq(userId))
                .orderBy(mr.week.asc())
                .orderBy(mr.pk.date.asc())
                .fetch();
        return moistureAsc;
    }
    public HeaderInfoDTO findHeaderInfo(String userID){
        QUser user = QUser.user;
        QTbl_goal goal = QTbl_goal.tbl_goal;

        HeaderInfoDTO headerInfo = queryFactory.select(Projections.constructor(HeaderInfoDTO.class, user.name, user.userId, user.birthdate, goal.goal, user.level))
                .from(user)
                .leftJoin(goal).on(user.userId.eq(goal.goalId))
                .where(user.userId.eq(userID))
                .fetchFirst();

        return headerInfo;
    }
    public List<PillSumDTO> findPillSum(String userId){
        QTbl_medication_record mr = QTbl_medication_record.tbl_medication_record;
        List<PillSumDTO> pillSum = queryFactory.select(Projections.constructor(PillSumDTO.class, mr.week, mr.finished.count()))
                .from(mr)
                .groupBy(mr.week)
                .orderBy(mr.week.asc())
                .where(mr.finished.eq(true))
                .where(mr.pk.id.eq(userId))
                .fetch();
        return pillSum;
    }


}

