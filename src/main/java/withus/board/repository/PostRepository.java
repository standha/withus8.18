package withus.board.repository;

import java.util.List;

// 리포지토리로 만들기 위해 JpaReprository 인터페이스 상속
import withus.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 검색 기능 라이브러리
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findBySubject(String subject);
    Post findBySubjectAndContent(String subject, String content);
    List<Post> findBySubjectLike(String subject);

    // Pageable 객체를 입력으로 받아 Page<Post> 타입 객체를 리턴하는 findAll 메서드를 생성
    // -> Service 수정
    Page<Post> findAll(Pageable pageable);

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Post q "
            + "left outer join User u1 on q.author=u1 "
            + "left outer join Comment a on a.post=q "
            + "left outer join User u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.userId like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.userId like %:kw% ")
    Page<Post> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
