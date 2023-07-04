package withus.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import withus.entity.User;
import lombok.Getter;
import lombok.Setter;

// 추천 기능에 필요한 라이브러리
import java.util.Set;
import javax.persistence.ManyToMany;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User author;

    private LocalDateTime modifyDate;

    // @ManyToMany 관계로 속성을 생성하면 새로운 테이블을 생성하여 데이터를 관리
    @ManyToMany
    Set<User> voter;
}
