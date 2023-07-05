package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_medication_record is a Querydsl query type for Tbl_medication_record
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_medication_record extends EntityPathBase<Tbl_medication_record> {

    private static final long serialVersionUID = 1640816991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_medication_record tbl_medication_record = new QTbl_medication_record("tbl_medication_record");

    public final BooleanPath finished = createBoolean("finished");

    public final QRecordKey pk;

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QTbl_medication_record(String variable) {
        this(Tbl_medication_record.class, forVariable(variable), INITS);
    }

    public QTbl_medication_record(Path<? extends Tbl_medication_record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_medication_record(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_medication_record(PathMetadata metadata, PathInits inits) {
        this(Tbl_medication_record.class, metadata, inits);
    }

    public QTbl_medication_record(Class<? extends Tbl_medication_record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

