package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWwithusEntry is a Querydsl query type for WwithusEntry
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWwithusEntry extends EntityPathBase<WwithusEntry> {

    private static final long serialVersionUID = -1965427368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWwithusEntry wwithusEntry = new QWwithusEntry("wwithusEntry");

    public final BooleanPath answer = createBoolean("answer");

    public final BooleanPath answerExpected = createBoolean("answerExpected");

    public final StringPath code = createString("code");

    public final StringPath content = createString("content");

    public final BooleanPath first = createBoolean("first");

    public final BooleanPath helpRequest = createBoolean("helpRequest");

    public final BooleanPath last = createBoolean("last");

    public final QWwithusEntry next;

    public final BooleanPath toRewind = createBoolean("toRewind");

    public final StringPath urlToAudioFile = createString("urlToAudioFile");

    public final StringPath urlToImageFile = createString("urlToImageFile");

    public QWwithusEntry(String variable) {
        this(WwithusEntry.class, forVariable(variable), INITS);
    }

    public QWwithusEntry(Path<? extends WwithusEntry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWwithusEntry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWwithusEntry(PathMetadata metadata, PathInits inits) {
        this(WwithusEntry.class, metadata, inits);
    }

    public QWwithusEntry(Class<? extends WwithusEntry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.next = inits.isInitialized("next") ? new QWwithusEntry(forProperty("next"), inits.get("next")) : null;
    }

}

