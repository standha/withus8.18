package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimeKey is a Querydsl query type for TimeKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QTimeKey extends BeanPath<TimeKey> {

    private static final long serialVersionUID = -325047695L;

    public static final QTimeKey timeKey = new QTimeKey("timeKey");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final TimePath<java.time.LocalTime> time = createTime("time", java.time.LocalTime.class);

    public QTimeKey(String variable) {
        super(TimeKey.class, forVariable(variable));
    }

    public QTimeKey(Path<? extends TimeKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeKey(PathMetadata metadata) {
        super(TimeKey.class, metadata);
    }

}

