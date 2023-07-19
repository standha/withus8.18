package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTbl_medication_alarm is a Querydsl query type for Tbl_medication_alarm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_medication_alarm extends EntityPathBase<Tbl_medication_alarm> {

    private static final long serialVersionUID = -101110909L;

    public static final QTbl_medication_alarm tbl_medication_alarm = new QTbl_medication_alarm("tbl_medication_alarm");

    public final BooleanPath alarmOnoffDinner = createBoolean("alarmOnoffDinner");

    public final BooleanPath alarmOnoffLunch = createBoolean("alarmOnoffLunch");

    public final BooleanPath alarmOnoffMorning = createBoolean("alarmOnoffMorning");

    public final StringPath id = createString("id");

    public final TimePath<java.time.LocalTime> medicationTimeDinner = createTime("medicationTimeDinner", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> medicationTimeLunch = createTime("medicationTimeLunch", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> medicationTimeMorning = createTime("medicationTimeMorning", java.time.LocalTime.class);

    public QTbl_medication_alarm(String variable) {
        super(Tbl_medication_alarm.class, forVariable(variable));
    }

    public QTbl_medication_alarm(Path<? extends Tbl_medication_alarm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTbl_medication_alarm(PathMetadata metadata) {
        super(Tbl_medication_alarm.class, metadata);
    }

}

