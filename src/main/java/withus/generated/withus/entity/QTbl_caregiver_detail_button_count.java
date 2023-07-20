package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_caregiver_detail_button_count is a Querydsl query type for Tbl_caregiver_detail_button_count
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_caregiver_detail_button_count extends EntityPathBase<Tbl_caregiver_detail_button_count> {

    private static final long serialVersionUID = -1322505249L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_caregiver_detail_button_count tbl_caregiver_detail_button_count = new QTbl_caregiver_detail_button_count("tbl_caregiver_detail_button_count");

    public final NumberPath<Integer> bloodPressureAlarm = createNumber("bloodPressureAlarm", Integer.class);

    public final NumberPath<Integer> bodyActivity = createNumber("bodyActivity", Integer.class);

    public final NumberPath<Integer> consulting = createNumber("consulting", Integer.class);

    public final NumberPath<Integer> deepBreath = createNumber("deepBreath", Integer.class);

    public final NumberPath<Integer> exerciseAlarm = createNumber("exerciseAlarm", Integer.class);

    public final QCaregiverProgressKey key;

    public final NumberPath<Integer> medicineAlarm = createNumber("medicineAlarm", Integer.class);

    public final NumberPath<Integer> meditation = createNumber("meditation", Integer.class);

    public final NumberPath<Integer> mindDiaryAlarm = createNumber("mindDiaryAlarm", Integer.class);

    public final NumberPath<Integer> mindScoreAlarm = createNumber("mindScoreAlarm", Integer.class);

    public final NumberPath<Integer> natriumMoistureAlarm = createNumber("natriumMoistureAlarm", Integer.class);

    public final NumberPath<Integer> recommendDiet = createNumber("recommendDiet", Integer.class);

    public final NumberPath<Integer> symptomAlarm = createNumber("symptomAlarm", Integer.class);

    public final NumberPath<Integer> waterIntakeAlarm = createNumber("waterIntakeAlarm", Integer.class);

    public final NumberPath<Integer> weightAlarm = createNumber("weightAlarm", Integer.class);

    public QTbl_caregiver_detail_button_count(String variable) {
        this(Tbl_caregiver_detail_button_count.class, forVariable(variable), INITS);
    }

    public QTbl_caregiver_detail_button_count(Path<? extends Tbl_caregiver_detail_button_count> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_caregiver_detail_button_count(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_caregiver_detail_button_count(PathMetadata metadata, PathInits inits) {
        this(Tbl_caregiver_detail_button_count.class, metadata, inits);
    }

    public QTbl_caregiver_detail_button_count(Class<? extends Tbl_caregiver_detail_button_count> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.key = inits.isInitialized("key") ? new QCaregiverProgressKey(forProperty("key")) : null;
    }

}

