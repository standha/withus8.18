package withus;

import withus.board.service.PostService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import withus.entity.User;
import withus.service.UserService;
//import withus.adminController.AdminHomeController;

import javax.annotation.security.RunAs;

@SpringBootTest
public class TestCase {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Test
    void testJPA() {
        for (int i=1; i<=100; i++) {
            String subject = String.format("테스트 데이터입니다 [%03d]", i);
            String content = String.format("내용무 [%03d]", i);
            User user = this.userService.getUserForBoard("admin");
            this.postService.create(subject, content, user, "질문게시판");
        }
    }
}
