package withus.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_goal")
public class Tbl_goal {
    @Id
    @Column(name = "goal_id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
    protected String goalId;

    @Column(name = "goal")
    private String goal;

    @Column(name = "top_goals")
    private String top_goals;

    @Column(name = "middle_goals")
    private String middle_goals;

    @Column(name = "bottom_goals")
    private String bottom_goals;

    @Column(name = "week")
    private Integer week;

//    @Column(name = "goalweek")
//    private String goalweek;

}
