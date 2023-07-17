package withus.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import withus.board.entity.Post;

import withus.entity.User;
import withus.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class RankController {
    private final UserService userService;

//    @GetMapping("/rank")
//    public String rank(Model model) {
//        List<User> rankList = this.userService.getUserForRank();
//        model.addAttribute("rankList", rankList);
//        return "board/rank";
//    }

    @GetMapping("/rank")
    public String rank() {
        return "redirect:/board/rank/patient";
    }

    @GetMapping("/rank/patient")
    public String rankPaitient(Model model, Principal principal) {
        List<User> rankList = this.userService.getListByType(User.Type.PATIENT);
        int index = 0;

        for (int i = 0; i < rankList.size(); i++){
            if (rankList.get(i).getUserId().equals(principal.getName())) {
                index = i + 1;
                break;
            }
        }

        User nowUser = this.userService.getUserById(principal.getName());

        model.addAttribute("rankList", rankList);
        model.addAttribute("nowUser", nowUser);
        model.addAttribute("index", index);

        return "board/rank";
    }

    @GetMapping("/rank/caregiver")
    public String rankCaregiver(Model model, Principal principal) {
        List<User> rankList = this.userService.getListByType(User.Type.CAREGIVER);
        int index = 0;

        for (int i = 0; i < rankList.size(); i++){
            if (rankList.get(i).getUserId().equals(principal.getName())) {
                index = i + 1;
                break;
            }
        }

        User nowUser = this.userService.getUserById(principal.getName());

        model.addAttribute("rankList", rankList);
        model.addAttribute("nowUser", nowUser);
        model.addAttribute("index", index);

        return "board/rank";
    }
}