package withus.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_patient_detail_button_count")
public class Tbl_patient_detail_button_count {
    @EmbeddedId
    private ProgressKey key;

    @Column(name = "recommendDiet")
    private Integer recommendDiet;

    @Column(name = "meditation")
    private Integer meditation;;

    @Column(name = "bodyActivity")
    private Integer bodyActivity;

    @Column(name = "deepBreath")
    private Integer deepBreath;

    @Column(name="consulting")
    private Integer consulting;

    @Column(name="medicineAlarm")
    private Integer medicineAlarm;

    @Column(name="bloodPressureAlarm")
    private Integer bloodPressureAlarm;

    @Column(name ="exerciseAlarm")
    private Integer exerciseAlarm;

    @Column(name="symptomAlarm")
    private Integer symptomAlarm;

    @Column(name ="natriumMoistureAlarm")
    private Integer natriumMoistureAlarm;

    @Column(name = "waterIntakeAlarm")
    private Integer waterIntakeAlarm;

    @Column(name="weightAlarm")
    private Integer weightAlarm;

    @Column(name="mindScoreAlarm")
    private Integer mindScoreAlarm;
}
