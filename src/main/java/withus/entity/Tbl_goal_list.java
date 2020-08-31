package withus.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "Tbl_goal_list")
public class Tbl_goal_list {
    @Id
    @Column(name = "registration_count", columnDefinition = "INTEGER(11)")
    private Integer registrationCount;

    @Column(name = "goal_number", columnDefinition = "INTEGER(11)")
    private Integer goalNumber;

    @Column(name = "achivement_rate", columnDefinition = "INTEGER(11)")
    @ColumnDefault("1")
    private Integer achivementRate;

    @Column(name = "goalSetting_time", columnDefinition = "DATE")
    private Date goalSettingTime;

}
