package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 931222028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final StringPath appToken = createString("appToken");

    public final DatePath<java.time.LocalDate> birthdate = createDate("birthdate", java.time.LocalDate.class);

    public final QUser caregiver;

    public final StringPath contact = createString("contact");

    public final EnumPath<User.Gender> gender = createEnum("gender", User.Gender.class);

    public final StringPath height = createString("height");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> registrationDateTime = createDateTime("registrationDateTime", java.time.LocalDateTime.class);

    public final EnumPath<User.Relative> relative = createEnum("relative", User.Relative.class);

    public final EnumPath<User.Type> type = createEnum("type", User.Type.class);

    public final StringPath userId = createString("userId");

    public final DateTimePath<java.time.LocalDateTime> userRecordDate = createDateTime("userRecordDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.caregiver = inits.isInitialized("caregiver") ? new QUser(forProperty("caregiver"), inits.get("caregiver")) : null;
    }

}

