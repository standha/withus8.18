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
@Table(name = "tbl_patient_duration_time")
public class Tbl_patient_duration_time {
    @EmbeddedId
    private ProgressKey key;

    @Column(name ="main")
    private Integer main;

    @Column(name = "goal")
    private Integer goal;

    @Column(name = "level")
    private Integer level;

    @Column(name = "withusRang")
    private Integer withusRang;

    @Column(name = "diseaseInfo")
    private Integer diseaseInfo;

    @Column(name = "medicine")
    private Integer medicine;

    @Column(name = "bloodPressure")
    private Integer bloodPressure;

    @Column(name = "exercise")
    private Integer exercise;

    @Column(name = "symptom")
    private Integer symptom;

    @Column(name = "natriumMoisture")
    private Integer natriumMoisture;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "mindHealth")
    private Integer mindHealth;

    @Column(name ="board")
    private Integer board;

    @Column(name = "alarm")
    private Integer alarm;

}
