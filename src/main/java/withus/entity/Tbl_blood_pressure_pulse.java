package withus.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_blood_pressure_pulse")
public class Tbl_blood_pressure_pulse {
    @Id
    @Column(name = "registration_count")
    private Integer registrationCount;

    @Column(name = "blood_record")
    private Integer bloodRecord;

    @Column(name = "systolic")
    private Integer systolic;

    @Column(name = "diastolic")
    private Integer diastolic;

    @Column(name = "pulse")
    private Integer pulse;

}
