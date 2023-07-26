package withus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Tbl_diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String category;

    @Column
    private String subject;

    @Column
    private String image;

    @Column
    private String ingredient;

    @Column
    private String sause;

    @Column
    private String step1;

    @Column
    private String step2;

    @Column
    private String step3;

    @Column
    private String step4;

    @Column
    private String step5;
}
