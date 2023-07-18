package withus.board.repository;

import org.springframework.stereotype.Repository;
import withus.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
