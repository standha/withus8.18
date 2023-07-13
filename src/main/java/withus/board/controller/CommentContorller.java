package withus.board.controller;

import withus.board.entity.Comment;
import withus.board.form.CommentForm;
import withus.board.service.CommentService;
import withus.board.entity.Post;
import withus.board.service.PostService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

// 현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용
import java.security.Principal;

// 작성자 조회 기능 추가
import withus.entity.User;
import withus.service.UserService;

// 수정 기능에 필요한 라이브러리
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/board/comment")
@RequiredArgsConstructor
@Controller
public class CommentContorller {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    // @PreAuthorize("isAuthenticated()") 애너테이션이 붙은 메서드는 로그인이 필요한 메서드를 의미
    // 만약 @PreAuthorize("isAuthenticated()") 애너테이션이 적용된 메서드가 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    // -> SecurityConfig
    @PreAuthorize("isAuthenticated()")
    // @PostMapping은 @GetMapping과 동일하게 매핑을 담당하는 역할을 하지만 POST요청만 받아들일 경우에 사용
    @PostMapping("/create/{id}")
    // createComment 메서드에 Principal 객체를 매개변수로 지정
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Post post = this.postService.getPost(id);
        // principal 객체를 통해 사용자명을 얻은 후에 사용자명을 통해 User 객체를 얻어서
        // 답변을 등록하는 CommentService의 create 메서드에 전달하여 답변을 저장
        User user = this.userService.getUserForBoard(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "board/post_detail";
        }

        Comment comment = this.commentService.create(post, commentForm.getContent(), user);

        return String.format("redirect:/board/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(Model model, CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);

        if (comment.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            commentForm.setContent(comment.getContent());
            return "board/comment_form_modify";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(Model model, @Valid CommentForm commentForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/comment_form_modify";
        }

        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);

        if (comment.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            this.commentService.modify(comment, commentForm.getContent());

            return String.format("redirect:/board/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            this.commentService.delete(comment);
            return String.format("redirect:/board/post/detail/%s", comment.getPost().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String commentVote(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        User user = this.userService.getUserForBoard(principal.getName());
        this.commentService.vote(comment, user);
        return String.format("redirect:/board/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
    }

}
