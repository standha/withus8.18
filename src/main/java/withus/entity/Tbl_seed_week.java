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
@Table(name = "tbl_seed_week")
public class Tbl_seed_week {
    @EmbeddedId
    private WeekUserKey key;

    @Column(name ="goal")
    private Integer goal;

    @Column(name ="level")
    private Boolean level;

}
