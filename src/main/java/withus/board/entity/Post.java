package withus.board.entity;

import java.time.LocalDateTime;
import java.util.List;

// sprintboot 2.X 버전은 jakarta -> javax
import javax.persistence.*;

// 추천 기능에 필요한 라이브러리
import java.util.Set;

import withus.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> commentList;


    // 여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로 @ManyToOne 관계가 성립
    @ManyToOne
    private User author;

    private LocalDateTime modifyDate;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<User> voter;

    private String category;
}
