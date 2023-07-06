package withus.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import withus.board.entity.Post;
import withus.board.service.PostService;
import withus.service.UserService;

import withus.board.service.PostService;
import withus.service.UserService;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;
    @GetMapping("/board")
    public String root(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Post> noticePaging = this.postService.getList(page, 1, kw);
        Page<Post> questionPaging = this.postService.getList(page, 1, kw);
        Page<Post> sharePaging = this.postService.getList(page, 1, kw);
        model.addAttribute("noticePaging", noticePaging);
        model.addAttribute("questionPaging", questionPaging);
        model.addAttribute("sharePaging", sharePaging);
        model.addAttribute("kw", kw);
        return "board/board";
    }
}