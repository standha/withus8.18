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
@Table(name = "tbl_goaloption")
public class Tbl_goaloption {

    @Id
    @Column(name = "goal_id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
    protected String goalId;

    @Column(name = "week")
    private Integer week;

    @Column(name = "goaloption")
    private String goaloption;

    @Column(name = "goalweek")
    private String goalweek;

}
