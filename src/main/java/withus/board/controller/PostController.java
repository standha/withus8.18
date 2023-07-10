package withus.board.controller;

import org.springframework.web.servlet.ModelAndView;
import withus.board.entity.Comment;
import withus.board.entity.Post;
import withus.board.form.PostForm;
import withus.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// 변하는 id 값을 얻을 때에는 위와 같이 @PathVariable 애너테이션을 사용
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 생성자 자동생성
import lombok.RequiredArgsConstructor;

// 입력값 검증
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import withus.board.form.CommentForm;
import withus.entity.User;
import withus.service.UserService;

// 페이징 라이브러리
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

// 수정 기능 구현에 필요한 라이브러리
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/post")
public class PostController {
    // final이 붙은 속성을 포함하는 생성자를 자동으로 생성
    // private final postRepository postRepository;
    // 레포지토리 대신 서비스를 사용하도록 수정
    private final PostService postService;
    private final UserService userService;

    // 페이징
    // http://localhost:8080/board/post/list?page=0 처럼 GET 방식으로 요청된 URL에서 page값을 가져오기 위해
    // @RequestParam(value="page", defaultValue="0") int page 매개변수가 list 메서드에 추가
    // 템플릿에 Page<Post> 객체인 paging을 전달
    // -> 기존에 전달했던 이름인 "postList" 대신 "paging" 이름으로 템플릿에 전달했기 때문에 템플릿도 변경
    /* 페이징 X
    @GetMapping("/list")
    public String list(Model model) {
        // postController의 list 메서드에서 조회한 질문 목록 데이터를 "postList"라는 이름으로 Model 객체에 저장
        List<Post> postList = this.postService.getList();
        model.addAttribute("postList", postList);
        return "post_list";
    }
    */

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Post> paging = this.postService.getList(page, 10, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board/post_list";
    }

    @GetMapping("/list/notice")
    public String listNotice(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Post> paging = this.postService.getList(page, 10, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board/post_list_notice";
    }

    @GetMapping("/list/question")
    public String listQuestion(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Post> paging = this.postService.getList(page, 10, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board/post_list_question";
    }

    @GetMapping("/list/share")
    public String listShare(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw){
        Page<Post> paging = this.postService.getList(page, 10, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board/post_list_share";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "board/post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "board/post_form";
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/create")
//    public String postCreate(Model model) {
//        model.addAttribute("action", "create");
//        return "board/post_form";
//    }

    // postCreate 메서드의 매개변수를 subject, content 대신 PostForm 객체로 변경
    // @Valid 애너테이션을 적용하면 postForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작
    // BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체
    // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다.
    // 만약 2개의 매개변수의 위치가 정확하지 않다면 @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.
    // 입력값도 입력하지 않았기 때문에 postForm의 @NotEmpty에 의해 Validation이 실패하여 다시 질문 등록 화면에 머물러 있을 것이다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board/post_form";
        }

        User user = this.userService.getUserForBoard(principal.getName());

        this.postService.create(postForm.getSubject(), postForm.getContent(), user, postForm.getCategory());
        return "redirect:/board";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(Model model, PostForm postForm, @PathVariable("id") Integer id, Principal principal) {

        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        if(post.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            postForm.setSubject(post.getSubject());
            postForm.setContent(post.getContent());
            postForm.setCategory(post.getCategory());
//        model.addAttribute("action", "modify");
            return "board/post_form_modify";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(Model model, @Valid PostForm postForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {
            return "board/post_form_modify";
        }

        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        if (post.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            this.postService.modify(post, postForm.getSubject(), postForm.getContent(), postForm.getCategory());

            return String.format("redirect:/board/post/detail/%s", id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (post.getAuthor().getUserId().equals(principal.getName()) || principal.getName().equals("admin")) {
            this.postService.delete(post);
            return "redirect:/board";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        User user = this.userService.getUserForBoard(principal.getName());
        this.postService.vote(post, user);
        return String.format("redirect:/board/post/detail/%s", id);
    }
}
