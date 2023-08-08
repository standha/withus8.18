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
@Table(name = "tbl_caregiver_seed_day")
public class Tbl_caregiver_seed_day {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "week")
    private Integer week;

    @Column(name = "medicine")
    private Boolean medicine;

    @Column(name ="blood_pressure")
    private Boolean bloodPressure;

    @Column(name ="exercise")
    private Boolean exercise;

    @Column(name ="diet_management")
    private Boolean dietManagement;

    @Column(name ="weight")
    private Boolean weight;

    @Column(name ="mind_diary")
    private Boolean mindDiary;

    @Column(name ="mind_score")
    private Boolean mindScore;

    @Column(name ="top_goal")
    private String topGoal;

    @Column(name ="middle_goal")
    private String middleGoal;

    @Column(name ="bottom_goal")
    private String bottomGoal;
}
