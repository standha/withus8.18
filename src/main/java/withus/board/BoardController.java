package withus.board;

import javafx.fxml.LoadException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import withus.board.entity.Post;
import withus.board.service.PostService;
import withus.entity.User;
import withus.service.UserService;

import withus.board.service.PostService;
import withus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;
    private final UserService userService;
    @GetMapping("/board")
    public String root(Model model) {
        List<Post> currentNotice = this.postService.getListByCategory("공지사항");
        List<Post> currentQuestion = this.postService.getListByCategory("질문게시판");
        List<Post> currentShare = this.postService.getListByCategory("나눔게시판");
        List<User> rankListPatient = this.userService.getListByType(User.Type.PATIENT);
        List<User> rankListCaregiver = this.userService.getListByType(User.Type.CAREGIVER);

        LocalDate localDateNow = LocalDate.now();

        model.addAttribute("currentNotice", currentNotice);
        model.addAttribute("currentQuestion", currentQuestion);
        model.addAttribute("currentShare", currentShare);
        model.addAttribute("rankListPatient", rankListPatient);
        model.addAttribute("rankListCaregiver", rankListCaregiver);
        model.addAttribute("localDateNow", localDateNow);
        return "board/board";
    }
}