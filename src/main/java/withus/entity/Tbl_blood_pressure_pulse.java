package withus.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_registration_count", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Tbl_patient patient;
}
