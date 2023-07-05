package withus.board.service;

import withus.board.entity.Post;
import withus.board.repository.PostRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import withus.board.DataNotFoundException;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

// 내림차순 조회 라이브러리
import java.util.ArrayList;

import org.springframework.data.domain.Sort;

import withus.entity.User;

// 검색 기능 라이브러리
import withus.board.entity.Comment;

import javax.management.QueryEval;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private Specification<Post> search(String kw) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Post, User> u1 = q.join("author", JoinType.LEFT);
                Join<Post, Comment> a = q.join("commentList", JoinType.LEFT);
                Join<Comment, User> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("userId"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("userId"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
    /* 페이징 X
    public List<Post> getList() {
        return this.postRepository.findAll();
    }
    */

    // 페이징
    // getList 메서드는 정수 타입의 페이지번호를 입력받아 해당 페이지의 질문 목록을 리턴하는 메서드로 변경
    // Pageable 객체를 생성할때 사용한 PageRequest.of(page, 10)에서 page는 조회할 페이지의 번호이고
    // 10은 한 페이지에 보여줄 게시물의 갯수를 의미
    // -> Post 서비스의 getList 메서드의 입출력 구조가 변경되었으므로 Post 컨트롤러도 수정
    public Page<Post> getList(int page, String kw) {
        // 내림차순 조회
        // Sort.Order 객체로 구성된 리스트에 Sort.Order 객체를 추가하고 Sort.by(소트리스트)로 소트 객체를 생성
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        // 게시물을 역순으로 조회하기 위해서는 위와 같이 PageRequest.of 메서드의 세번째 파라미터로 Sort 객체를 전달
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Post> spec = search(kw);
        return this.postRepository.findAll(spec, pageable);
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        // Post 객체는 Optional 객체이기 때문에 위와 같이 isPresent 메서드로 해당 데이터가 존재하는지 검사하는 로직이 필요
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void create(String subject, String content, User user, String category) {
        Post q = new Post();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        q.setCategory(category);
        this.postRepository.save(q);
    }

    public void modify(Post post, String subject, String content, String category) {
        post.setSubject(subject);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        post.setCategory(category);
        this.postRepository.save(post);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public void vote(Post post, User user) {
        post.getVoter().add(user);
        this.postRepository.save(post);
    }
}
