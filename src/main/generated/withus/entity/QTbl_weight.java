package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_weight is a Querydsl query type for Tbl_weight
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_weight extends EntityPathBase<Tbl_weight> {

    private static final long serialVersionUID = -90906726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_weight tbl_weight = new QTbl_weight("tbl_weight");

    public final QRecordKey pk;

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QTbl_weight(String variable) {
        this(Tbl_weight.class, forVariable(variable), INITS);
    }

    public QTbl_weight(Path<? extends Tbl_weight> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_weight(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_weight(PathMetadata metadata, PathInits inits) {
        this(Tbl_weight.class, metadata, inits);
    }

    public QTbl_weight(Class<? extends Tbl_weight> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

