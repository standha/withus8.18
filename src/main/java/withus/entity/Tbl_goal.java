package withus.entity;

import lombok.*;

import javax.persistence.*;

@ToString
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

    @Column(name = "Goal")
    private Integer goal;

    @Column(columnDefinition = "VARCHAR(16) NOT NULL", length = 16)
	@Enumerated(EnumType.STRING)
    //@Column(name = "GoalTest")
    private GoalType goaltype;

    public enum GoalType{
        GOAL1, GOAL2
    }
}
