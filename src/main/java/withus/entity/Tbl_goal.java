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
    @EmbeddedId
    private RecordKey pk;

    @Id
    @Column(name = "goal_id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
    protected String goalId;

    @Column(name = "goal")
    private Integer goal;

    @Column(name = "top_goals")
    private Integer top_goals;

    @Column(name = "middle_goals")
    private Integer middle_goals;

    @Column(name = "bottom_goals")
    private Integer bottom_goals;

    @Column(name = "week")
    private Integer week;


}
