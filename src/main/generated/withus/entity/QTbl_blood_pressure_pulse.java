package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTbl_blood_pressure_pulse is a Querydsl query type for Tbl_blood_pressure_pulse
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_blood_pressure_pulse extends EntityPathBase<Tbl_blood_pressure_pulse> {

    private static final long serialVersionUID = 788970406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTbl_blood_pressure_pulse tbl_blood_pressure_pulse = new QTbl_blood_pressure_pulse("tbl_blood_pressure_pulse");

    public final NumberPath<Integer> contraction = createNumber("contraction", Integer.class);

    public final QRecordKey pk;

    public final NumberPath<Integer> pressure = createNumber("pressure", Integer.class);

    public final NumberPath<Integer> relaxation = createNumber("relaxation", Integer.class);

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public QTbl_blood_pressure_pulse(String variable) {
        this(Tbl_blood_pressure_pulse.class, forVariable(variable), INITS);
    }

    public QTbl_blood_pressure_pulse(Path<? extends Tbl_blood_pressure_pulse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTbl_blood_pressure_pulse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTbl_blood_pressure_pulse(PathMetadata metadata, PathInits inits) {
        this(Tbl_blood_pressure_pulse.class, metadata, inits);
    }

    public QTbl_blood_pressure_pulse(Class<? extends Tbl_blood_pressure_pulse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pk = inits.isInitialized("pk") ? new QRecordKey(forProperty("pk")) : null;
    }

}

