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

}
