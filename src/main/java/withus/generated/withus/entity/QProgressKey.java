package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProgressKey is a Querydsl query type for ProgressKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QProgressKey extends BeanPath<ProgressKey> {

    private static final long serialVersionUID = 1022678929L;

    public static final QProgressKey progressKey = new QProgressKey("progressKey");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QProgressKey(String variable) {
        super(ProgressKey.class, forVariable(variable));
    }

    public QProgressKey(Path<? extends ProgressKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgressKey(PathMetadata metadata) {
        super(ProgressKey.class, metadata);
    }

}

