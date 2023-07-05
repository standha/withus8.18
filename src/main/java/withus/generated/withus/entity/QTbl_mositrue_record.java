package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_mositrue_record is a Querydsl query type for Tbl_mositrue_record
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_mositrue_record extends EntityPathBase<Tbl_mositrue_record> {

    private static final long serialVersionUID = -2060014904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_mositrue_record tbl_mositrue_record = new QTbl_mositrue_record("tbl_mositrue_record");

    public final NumberPath<Integer> intake = createNumber("intake", Integer.class);

    public final QRecordKey pk;

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QTbl_mositrue_record(String variable) {
        this(Tbl_mositrue_record.class, forVariable(variable), INITS);
    }

    public QTbl_mositrue_record(Path<? extends Tbl_mositrue_record> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_mositrue_record(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_mositrue_record(PathMetadata metadata, PathInits inits) {
        this(Tbl_mositrue_record.class, metadata, inits);
    }

    public QTbl_mositrue_record(Class<? extends Tbl_mositrue_record> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

