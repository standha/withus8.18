package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCaregiverProgressKey is a Querydsl query type for CaregiverProgressKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QCaregiverProgressKey extends BeanPath<CaregiverProgressKey> {

    private static final long serialVersionUID = 1351760899L;

    public static final QCaregiverProgressKey caregiverProgressKey = new QCaregiverProgressKey("caregiverProgressKey");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QCaregiverProgressKey(String variable) {
        super(CaregiverProgressKey.class, forVariable(variable));
    }

    public QCaregiverProgressKey(Path<? extends CaregiverProgressKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCaregiverProgressKey(PathMetadata metadata) {
        super(CaregiverProgressKey.class, metadata);
    }

}

