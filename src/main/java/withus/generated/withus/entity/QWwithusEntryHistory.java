package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWwithusEntryHistory is a Querydsl query type for WwithusEntryHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWwithusEntryHistory extends EntityPathBase<WwithusEntryHistory> {

    private static final long serialVersionUID = 249058332L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWwithusEntryHistory wwithusEntryHistory = new QWwithusEntryHistory("wwithusEntryHistory");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final QWwithusEntryHistory_Key key;

    public QWwithusEntryHistory(String variable) {
        this(WwithusEntryHistory.class, forVariable(variable), INITS);
    }

    public QWwithusEntryHistory(Path<? extends WwithusEntryHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWwithusEntryHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWwithusEntryHistory(PathMetadata metadata, PathInits inits) {
        this(WwithusEntryHistory.class, metadata, inits);
    }

    public QWwithusEntryHistory(Class<? extends WwithusEntryHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.key = inits.isInitialized("key") ? new QWwithusEntryHistory_Key(forProperty("key"), inits.get("key")) : null;
    }

}

