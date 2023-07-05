package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_caregiver_use_time is a Querydsl query type for Tbl_caregiver_use_time
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_caregiver_use_time extends EntityPathBase<Tbl_caregiver_use_time> {

    private static final long serialVersionUID = -599457546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_caregiver_use_time tbl_caregiver_use_time = new QTbl_caregiver_use_time("tbl_caregiver_use_time");

    public final NumberPath<Integer> alarm = createNumber("alarm", Integer.class);

    public final NumberPath<Integer> bloodPressure = createNumber("bloodPressure", Integer.class);

    public final NumberPath<Integer> board = createNumber("board", Integer.class);

    public final NumberPath<Integer> dietManagement = createNumber("dietManagement", Integer.class);

    public final NumberPath<Integer> diseaseInfo = createNumber("diseaseInfo", Integer.class);

    public final NumberPath<Integer> exercise = createNumber("exercise", Integer.class);

    public final NumberPath<Integer> familyObservation = createNumber("familyObservation", Integer.class);

    public final NumberPath<Integer> goal = createNumber("goal", Integer.class);

    public final QCaregiverProgressKey key;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> main = createNumber("main", Integer.class);

    public final NumberPath<Integer> medicine = createNumber("medicine", Integer.class);

    public final NumberPath<Integer> mindHealth = createNumber("mindHealth", Integer.class);

    public final NumberPath<Integer> totalUserTime = createNumber("totalUserTime", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public final NumberPath<Integer> withusRang = createNumber("withusRang", Integer.class);

    public QTbl_caregiver_use_time(String variable) {
        this(Tbl_caregiver_use_time.class, forVariable(variable), INITS);
    }

    public QTbl_caregiver_use_time(Path<? extends Tbl_caregiver_use_time> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_caregiver_use_time(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_caregiver_use_time(PathMetadata metadata, PathInits inits) {
        this(Tbl_caregiver_use_time.class, metadata, inits);
    }

    public QTbl_caregiver_use_time(Class<? extends Tbl_caregiver_use_time> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.key = inits.isInitialized("key") ? new QCaregiverProgressKey(forProperty("key")) : null;
    }

}

