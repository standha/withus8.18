package withus.board.service;

import withus.board.DataNotFoundException;
import withus.board.entity.Comment;
import withus.board.repository.CommentRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import withus.board.entity.Post;

import java.time.LocalDateTime;
import java.util.Optional;

import withus.entity.User;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Post post, String content, User author) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        // create 메서드에 User 객체를 추가로 전달받아 답변 저장시 author 속성에 세팅
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void vote(Comment comment, User user) {
        comment.getVoter().add(user);
        this.commentRepository.save(comment);
    }
}
