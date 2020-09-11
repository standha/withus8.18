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
@Table(name = "tbl_weight")
public class Tbl_weight {
    @EmbeddedId
    private RecordKey pk;

    @Getter
    @Column(name = "Weight")
    private float weight;

}
