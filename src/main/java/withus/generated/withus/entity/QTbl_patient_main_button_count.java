package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_patient_main_button_count is a Querydsl query type for Tbl_patient_main_button_count
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_patient_main_button_count extends EntityPathBase<Tbl_patient_main_button_count> {

    private static final long serialVersionUID = -851370132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_patient_main_button_count tbl_patient_main_button_count = new QTbl_patient_main_button_count("tbl_patient_main_button_count");

    public final NumberPath<Integer> alarm = createNumber("alarm", Integer.class);

    public final NumberPath<Integer> bloodPressure = createNumber("bloodPressure", Integer.class);

    public final NumberPath<Integer> board = createNumber("board", Integer.class);

    public final NumberPath<Integer> diseaseInfo = createNumber("diseaseInfo", Integer.class);

    public final NumberPath<Integer> exercise = createNumber("exercise", Integer.class);

    public final NumberPath<Integer> goal = createNumber("goal", Integer.class);

    public final NumberPath<Integer> helper = createNumber("helper", Integer.class);

    public final NumberPath<Integer> infoEdit = createNumber("infoEdit", Integer.class);

    public final QProgressKey key;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> medicine = createNumber("medicine", Integer.class);

    public final NumberPath<Integer> mindHealth = createNumber("mindHealth", Integer.class);

    public final NumberPath<Integer> natriumMoisture = createNumber("natriumMoisture", Integer.class);

    public final NumberPath<Integer> symptom = createNumber("symptom", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public final NumberPath<Integer> withusRang = createNumber("withusRang", Integer.class);

    public QTbl_patient_main_button_count(String variable) {
        this(Tbl_patient_main_button_count.class, forVariable(variable), INITS);
    }

    public QTbl_patient_main_button_count(Path<? extends Tbl_patient_main_button_count> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_patient_main_button_count(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_patient_main_button_count(PathMetadata metadata, PathInits inits) {
        this(Tbl_patient_main_button_count.class, metadata, inits);
    }

    public QTbl_patient_main_button_count(Class<? extends Tbl_patient_main_button_count> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.key = inits.isInitialized("key") ? new QProgressKey(forProperty("key")) : null;
    }

}

