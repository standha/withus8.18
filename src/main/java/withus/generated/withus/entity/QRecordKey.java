package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecordKey is a Querydsl query type for RecordKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRecordKey extends BeanPath<RecordKey> {

    private static final long serialVersionUID = 2137548461L;

    public static final QRecordKey recordKey = new QRecordKey("recordKey");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public QRecordKey(String variable) {
        super(RecordKey.class, forVariable(variable));
    }

    public QRecordKey(Path<? extends RecordKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecordKey(PathMetadata metadata) {
        super(RecordKey.class, metadata);
    }

}

