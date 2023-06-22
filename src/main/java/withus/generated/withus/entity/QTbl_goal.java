package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTbl_goal is a Querydsl query type for Tbl_goal
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_goal extends EntityPathBase<Tbl_goal> {

    private static final long serialVersionUID = 857537717L;

    public static final QTbl_goal tbl_goal = new QTbl_goal("tbl_goal");

    public final NumberPath<Integer> goal = createNumber("goal", Integer.class);

    public final StringPath goalId = createString("goalId");

    public QTbl_goal(String variable) {
        super(Tbl_goal.class, forVariable(variable));
    }

    public QTbl_goal(Path<? extends Tbl_goal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTbl_goal(PathMetadata metadata) {
        super(Tbl_goal.class, metadata);
    }

}

