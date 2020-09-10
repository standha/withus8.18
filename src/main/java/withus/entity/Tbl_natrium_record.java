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
    @Enumerated(EnumType.STRING)
    private  Salt morning;

    @Column(name = "launch")
    @Enumerated(EnumType.STRING)
    private Salt launch;

    @Column(name = "dinner")
    @Enumerated(EnumType.STRING)
    private Salt dinner;

    public enum Salt{
        NONE, LOW, MEDIUM, HIGH
    }
}
