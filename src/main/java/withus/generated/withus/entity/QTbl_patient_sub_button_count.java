package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_patient_sub_button_count is a Querydsl query type for Tbl_patient_sub_button_count
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_patient_sub_button_count extends EntityPathBase<Tbl_patient_sub_button_count> {

    private static final long serialVersionUID = 420649981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_patient_sub_button_count tbl_patient_sub_button_count = new QTbl_patient_sub_button_count("tbl_patient_sub_button_count");

    public final NumberPath<Integer> highLevel = createNumber("highLevel", Integer.class);

    public final NumberPath<Integer> hof = createNumber("hof", Integer.class);

    public final QProgressKey key;

    public final NumberPath<Integer> lowLevel = createNumber("lowLevel", Integer.class);

    public final NumberPath<Integer> makeMyGoal = createNumber("makeMyGoal", Integer.class);

    public final NumberPath<Integer> medicineTime = createNumber("medicineTime", Integer.class);

    public final NumberPath<Integer> middleLevel = createNumber("middleLevel", Integer.class);

    public final NumberPath<Integer> mindDiary = createNumber("mindDiary", Integer.class);

    public final NumberPath<Integer> mindManagement = createNumber("mindManagement", Integer.class);

    public final NumberPath<Integer> mindScore = createNumber("mindScore", Integer.class);

    public final NumberPath<Integer> natriumMoisture = createNumber("natriumMoisture", Integer.class);

    public final NumberPath<Integer> notice = createNumber("notice", Integer.class);

    public final NumberPath<Integer> outPatientVisitTime = createNumber("outPatientVisitTime", Integer.class);

    public final NumberPath<Integer> question = createNumber("question", Integer.class);

    public final NumberPath<Integer> share = createNumber("share", Integer.class);

    public final NumberPath<Integer> waterIntake = createNumber("waterIntake", Integer.class);

    public QTbl_patient_sub_button_count(String variable) {
        this(Tbl_patient_sub_button_count.class, forVariable(variable), INITS);
    }

    public QTbl_patient_sub_button_count(Path<? extends Tbl_patient_sub_button_count> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_patient_sub_button_count(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_patient_sub_button_count(PathMetadata metadata, PathInits inits) {
        this(Tbl_patient_sub_button_count.class, metadata, inits);
    }

    public QTbl_patient_sub_button_count(Class<? extends Tbl_patient_sub_button_count> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.key = inits.isInitialized("key") ? new QProgressKey(forProperty("key")) : null;
    }

}

