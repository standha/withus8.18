package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_helper_request is a Querydsl query type for Tbl_helper_request
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_helper_request extends EntityPathBase<Tbl_helper_request> {

    private static final long serialVersionUID = -737511904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_helper_request tbl_helper_request = new QTbl_helper_request("tbl_helper_request");

    public final QTimeKey pk;

    public QTbl_helper_request(String variable) {
        this(Tbl_helper_request.class, forVariable(variable), INITS);
    }

    public QTbl_helper_request(Path<? extends Tbl_helper_request> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_helper_request(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_helper_request(PathMetadata metadata, PathInits inits) {
        this(Tbl_helper_request.class, metadata, inits);
    }

    public QTbl_helper_request(Class<? extends Tbl_helper_request> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QTimeKey(forProperty("pk")) : null;
    }

}

