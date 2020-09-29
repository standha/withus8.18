package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_natrium_record is a Querydsl query type for Tbl_natrium_record
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_natrium_record extends EntityPathBase<Tbl_natrium_record> {

    private static final long serialVersionUID = 825402082L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_natrium_record tbl_natrium_record = new QTbl_natrium_record("tbl_natrium_record");

    public final NumberPath<Integer> dinner = createNumber("dinner", Integer.class);

    public final NumberPath<Integer> lunch = createNumber("lunch", Integer.class);

    public final NumberPath<Integer> morning = createNumber("morning", Integer.class);

    public final QRecordKey pk;

    public QTbl_natrium_record(String variable) {
        this(Tbl_natrium_record.class, forVariable(variable), INITS);
    }

    public QTbl_natrium_record(Path<? extends Tbl_natrium_record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_natrium_record(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_natrium_record(PathMetadata metadata, PathInits inits) {
        this(Tbl_natrium_record.class, metadata, inits);
    }

    public QTbl_natrium_record(Class<? extends Tbl_natrium_record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

