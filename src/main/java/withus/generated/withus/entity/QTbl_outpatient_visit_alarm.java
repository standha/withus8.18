package withus.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTbl_outpatient_visit_alarm is a Querydsl query type for Tbl_outpatient_visit_alarm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTbl_outpatient_visit_alarm extends EntityPathBase<Tbl_outpatient_visit_alarm> {

    private static final long serialVersionUID = -753595593L;

    public static final QTbl_outpatient_visit_alarm tbl_outpatient_visit_alarm = new QTbl_outpatient_visit_alarm("tbl_outpatient_visit_alarm");

    public final StringPath id = createString("id");

    public final DatePath<java.time.LocalDate> outPatientVisitDate = createDate("outPatientVisitDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> outPatientVisitTime = createTime("outPatientVisitTime", java.time.LocalTime.class);

    public final BooleanPath visitAlarm = createBoolean("visitAlarm");

    public QTbl_outpatient_visit_alarm(String variable) {
        super(Tbl_outpatient_visit_alarm.class, forVariable(variable));
    }

    public QTbl_outpatient_visit_alarm(Path<? extends Tbl_outpatient_visit_alarm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTbl_outpatient_visit_alarm(PathMetadata metadata) {
        super(Tbl_outpatient_visit_alarm.class, metadata);
    }

}

