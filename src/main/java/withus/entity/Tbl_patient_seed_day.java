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
@Table(name = "tbl_patient_seed_day")
public class Tbl_patient_seed_day {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "week")
    private Integer week;

    @Column(name = "medicine", columnDefinition = "int default 0")
    private Integer medicine;

    @Column(name ="blood_pressure", columnDefinition = "int default 0")
    private Integer bloodPressure;

    @Column(name ="exercise", columnDefinition = "int default 0")
    private Integer exercise;

    @Column(name ="natirum_moisture", columnDefinition = "int default 0")
    private Integer natirumMoisture;

    @Column(name ="water_intake", columnDefinition = "int default 0")
    private Integer waterIntake;

    @Column(name ="symptom", columnDefinition = "int default 0")
    private Integer symptom;

    @Column(name ="weight", columnDefinition = "int default 0")
    private Integer weight;

    @Column(name ="mind_diary", columnDefinition = "int default 0")
    private Integer mindDiary;

    @Column(name ="mind_score", columnDefinition = "int default 0")
    private Integer mindScore;

    @Column(name ="top_goal")
    private String topGoal;

    @Column(name ="middle_goal")
    private String middleGoal;

    @Column(name ="bottom_goal")
    private String bottomGoal;
}
