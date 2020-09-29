package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWwithusEntryHistory_Key is a Querydsl query type for Key
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QWwithusEntryHistory_Key extends BeanPath<WwithusEntryHistory.Key> {

    private static final long serialVersionUID = -2077297299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWwithusEntryHistory_Key key = new QWwithusEntryHistory_Key("key1");

    public final QWwithusEntry entry;

    public final QUser user;

    public QWwithusEntryHistory_Key(String variable) {
        this(WwithusEntryHistory.Key.class, forVariable(variable), INITS);
    }

    public QWwithusEntryHistory_Key(Path<? extends WwithusEntryHistory.Key> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWwithusEntryHistory_Key(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWwithusEntryHistory_Key(PathMetadata metadata, PathInits inits) {
        this(WwithusEntryHistory.Key.class, metadata, inits);
    }

    public QWwithusEntryHistory_Key(Class<? extends WwithusEntryHistory.Key> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.entry = inits.isInitialized("entry") ? new QWwithusEntry(forProperty("entry"), inits.get("entry")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

