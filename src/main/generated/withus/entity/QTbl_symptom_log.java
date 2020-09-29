package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_symptom_log is a Querydsl query type for Tbl_symptom_log
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_symptom_log extends EntityPathBase<Tbl_symptom_log> {

    private static final long serialVersionUID = 1140755532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_symptom_log tbl_symptom_log = new QTbl_symptom_log("tbl_symptom_log");

    public final NumberPath<Integer> ankle = createNumber("ankle", Integer.class);

    public final NumberPath<Integer> cough = createNumber("cough", Integer.class);

    public final NumberPath<Integer> outofbreath = createNumber("outofbreath", Integer.class);

    public final QRecordKey pk;

    public final NumberPath<Integer> tired = createNumber("tired", Integer.class);

    public final NumberPath<Integer> todaysymptom = createNumber("todaysymptom", Integer.class);

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QTbl_symptom_log(String variable) {
        this(Tbl_symptom_log.class, forVariable(variable), INITS);
    }

    public QTbl_symptom_log(Path<? extends Tbl_symptom_log> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_symptom_log(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_symptom_log(PathMetadata metadata, PathInits inits) {
        this(Tbl_symptom_log.class, metadata, inits);
    }

    public QTbl_symptom_log(Class<? extends Tbl_symptom_log> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

