package withus.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_exercise_record")
public class Tbl_Exercise_record {
    @EmbeddedId
    private RecordKey pk;

    @Column(name = "hour", columnDefinition = "int default 0")
    private Integer hour;

    @Column(name = "minute", columnDefinition = "int default 0")
    private Integer minute;

    @Column(name = "week")
    private Integer week;

    @Column(name = "walking")
    private Integer walking;

    @Column(name = "cycling")
    private Integer cycling;

    @Column(name = "swimming")
    private Integer swimming;

    @Column(name = "strength")
    private Integer strength;

    @Column(name ="recentExercise")
    private Integer recentExercise;


}
