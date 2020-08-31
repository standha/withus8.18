package withus.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_outpatient_visit_alarm")
public class Tbl_outpatient_visit_alarm {
    @Id
    @Column(name = "registration_Count")
    private Integer registrationCount;

    @Column(name = "visit_Time")
    private Date outPatientVisitTime;

    @Column(name = "visit_Alarm")
    @ColumnDefault("0")
    private Boolean visitAlarm;
}
