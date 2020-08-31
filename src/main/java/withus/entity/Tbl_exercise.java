package withus.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_exercise")
public class Tbl_exercise {
    @Id
    @Column(name = "registration_count")
    private Integer registrationCount;

    @Column(name = "exercise_record")
    private Date exerciseRecord;

    @Column(name = "exercise_hour")
    private Integer exerciseHour;

    @Column(name = "exercise_min")
    private Integer exerciseMin;
}
