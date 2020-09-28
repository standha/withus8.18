package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_Exercise_record is a Querydsl query type for Tbl_Exercise_record
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_Exercise_record extends EntityPathBase<Tbl_Exercise_record> {

    private static final long serialVersionUID = 58779030L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_Exercise_record tbl_Exercise_record = new QTbl_Exercise_record("tbl_Exercise_record");

    public final NumberPath<Integer> hour = createNumber("hour", Integer.class);

    public final NumberPath<Integer> minute = createNumber("minute", Integer.class);

    public final QRecordKey pk;

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QTbl_Exercise_record(String variable) {
        this(Tbl_Exercise_record.class, forVariable(variable), INITS);
    }

    public QTbl_Exercise_record(Path<? extends Tbl_Exercise_record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_Exercise_record(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_Exercise_record(PathMetadata metadata, PathInits inits) {
        this(Tbl_Exercise_record.class, metadata, inits);
    }

    public QTbl_Exercise_record(Class<? extends Tbl_Exercise_record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

