package withus.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer goal;

    @Column(name = "top_goals", columnDefinition = "VARCHAR(128)", length = 128)
    private Integer top_goals;

    @Column(name = "middle_goals", columnDefinition = "VARCHAR(128)", length = 128)
    private Integer middle_goals;

    @Column(name = "bottom_goals", columnDefinition = "VARCHAR(128)", length = 128)
    private Integer bottom_goals;
}
