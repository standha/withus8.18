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
@Table(name = "tbl_patient_sub_button_count")
public class Tbl_patient_sub_button_count {
    @EmbeddedId
    private ProgressKey key;

    @Column(name = "lowLevel")
    private Integer lowLevel;

    @Column(name = "middleLevel")
    private Integer middleLevel;

    @Column(name = "highLevel")
    private Integer highLevel;

    @Column(name = "makeMyGoal")
    private Integer makeMyGoal;

    @Column(name = "natriumMoisture")
    private Integer natriumMoisture;

    @Column(name ="waterIntake")
    private Integer waterIntake;

    @Column(name ="mindDiary")
    private Integer mindDiary;

    @Column(name="mindScore")
    private Integer mindScore;

    @Column(name="mindManagement")
    private Integer mindManagement;
    //hall of fame 명예의 전당
    @Column(name="hof")
    private Integer hof;

    @Column(name="notice")
    private Integer notice;

    @Column(name="question")
    private Integer question;

    @Column(name="share")
    private Integer share;

    @Column(name="medicineTime")
    private Integer medicineTime;

    @Column(name="outPatientVisitTime")
    private Integer outPatientVisitTime;
}
