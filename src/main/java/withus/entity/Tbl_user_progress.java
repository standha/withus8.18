package withus.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
@Table(name = "tbl_user_progress")
public class  Tbl_user_progress {
    @Id
    @Column(name = "registration_Count", columnDefinition = "INTEGER(11)")
    private Integer registrationCount;

    @Column(name = "week_Progress",columnDefinition = "int(11) NOT NULL default '0'")
    @Builder.Default
    private Integer weekProgress = 0;

    @Column(name = "withusrang_Progress", columnDefinition = "VARCHAR(20)")
    private String withusrangProgress;

}
