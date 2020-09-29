package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWithusHelpRequest is a Querydsl query type for WithusHelpRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWithusHelpRequest extends EntityPathBase<WithusHelpRequest> {

    private static final long serialVersionUID = 30471913L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWithusHelpRequest withusHelpRequest = new QWithusHelpRequest("withusHelpRequest");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final BooleanPath dealtWith = createBoolean("dealtWith");

    public final NumberPath<Integer> requestId = createNumber("requestId", Integer.class);

    public final QUser user;

    public QWithusHelpRequest(String variable) {
        this(WithusHelpRequest.class, forVariable(variable), INITS);
    }

    public QWithusHelpRequest(Path<? extends WithusHelpRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWithusHelpRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWithusHelpRequest(PathMetadata metadata, PathInits inits) {
        this(WithusHelpRequest.class, metadata, inits);
    }

    public QWithusHelpRequest(Class<? extends WithusHelpRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

