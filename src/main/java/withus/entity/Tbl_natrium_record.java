package withus.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "Tbl_natrium_record")
public class Tbl_natrium_record {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "morning")
    private Integer morning;

    @Column(name = "lunch")
    private Integer lunch;

    @Column(name = "dinner")
    private Integer dinner;

    @Column(name = "week")
    private Integer week;
}
