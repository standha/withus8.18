package withus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import withus.entity.*;
import withus.service.CountService;
import withus.service.DietService;
import withus.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diet")
public class DietController {

    private final DietService dietService;
    private final UserService userService;
    private final CountService countService;

    //    soup1: 북엇국
    //    soup2: 들깨버섯찌개
    //    soup3: 낙지감잣국
    //    soup4: 소고기뭇국
    //    soup5: 단호박배추된장국
    //    soup6: 바지락맑은국
    //    soup7: 버섯순두부찌개
    //    soup8: 닭곰탕
    //
    //    side1: 감자카레볶음
    //    side2: 뱅어포볶음
    //    side3: 다시다두부말이
    //    side4: 황태조림
    //    side5: 브로콜리견과류볶음
    //    side6: 느타리버섯볶음
    //    side7: 소고기채소불고기
    //    side8: 시래기닭조림
    //
    //    kimchi1: 상추겉절이
    //    kimchi2: 오이생채
    //    kimchi3: 참외겉절이
    //    kimchi4: 채소피클
    //    kimchi5: 고추김치
    //    kimchi6: 콜라비깍두기
    //    kimchi7: 청경채김치
    //    kimchi8: 저염겉절이

    @GetMapping("/soup")
    public String soupList(Model model, Principal principal) {
        List<Tbl_diet> soupList = this.dietService.getListByCategory("soup");

        User user = this.userService.getUserById(principal.getName());
        User.Type type = user.getType();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        } else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        }

        model.addAttribute("user", user);
        model.addAttribute("type", type);
        model.addAttribute("list", soupList);


        return "moistureNatrium/diet/list";
    }

    @GetMapping("/side")
    public String sideList(Model model, Principal principal) {
        List<Tbl_diet> sideList = this.dietService.getListByCategory("side");

        User user = this.userService.getUserById(principal.getName());
        User.Type type = user.getType();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        } else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        }

        model.addAttribute("user", user);
        model.addAttribute("type", type);
        model.addAttribute("list", sideList);

        return "moistureNatrium/diet/list";
    }

    @GetMapping("/kimchi")
    public String kimchiList(Model model, Principal principal) {
        List<Tbl_diet> kimchiList = this.dietService.getListByCategory("kimchi");

        User user = this.userService.getUserById(principal.getName());
        User.Type type = user.getType();
        if (user.getType() == User.Type.PATIENT) {
            Tbl_patient_main_button_count count = countService.getPatientMainCount(new ProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        } else if (user.getType() == User.Type.CAREGIVER) {
            Tbl_caregiver_main_button_count count = countService.getCaregiverMainCount(new CaregiverProgressKey(user.getUserId(), user.getWeek()));
            model.addAttribute("count", count);
        }

        model.addAttribute("user", user);
        model.addAttribute("type", type);
        model.addAttribute("list", kimchiList);

        return "moistureNatrium/diet/list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Tbl_diet diet = this.dietService.getDiet(id);
        model.addAttribute("diet", diet);

        return "moistureNatrium/diet/detail";
    }










//    // 리스트
//    @GetMapping("/soup")
//    public String soup() {
//        return "moistureNatrium/diet/soup/list";
//    }
//
//    @GetMapping("/side")
//    public String side() {
//        return "moistureNatrium/diet/side/list";
//    }
//
//    @GetMapping("/kimchi")
//    public String kimchi() {
//        return "moistureNatrium/diet/kimchi/list";
//    }
//
//    // 국 상세
//    @GetMapping("/soup/1")
//    public String soup1() {
//        return "moistureNatrium/diet/soup/1";
//    }
//
//    @GetMapping("/soup/2")
//    public String soup2() {
//        return "moistureNatrium/diet/soup/2";
//    }
//
//    @GetMapping("/soup/3")
//    public String soup3() {
//        return "moistureNatrium/diet/soup/3";
//    }
//
//    @GetMapping("/soup/4")
//    public String soup4() {
//        return "moistureNatrium/diet/soup/4";
//    }
//
//    @GetMapping("/soup/5")
//    public String soup5() {
//        return "moistureNatrium/diet/soup/6";
//    }
//
//    @GetMapping("/soup/6")
//    public String soup6() {
//        return "moistureNatrium/diet/soup/6";
//    }
//
//    @GetMapping("/soup/7")
//    public String soup7() {
//        return "moistureNatrium/diet/soup/7";
//    }
//
//    @GetMapping("/soup/8")
//    public String soup8() {
//        return "moistureNatrium/diet/soup/8";
//    }
//
//    // 반찬 상세
//    @GetMapping("/side/1")
//    public String side1() {
//        return "moistureNatrium/diet/side/1";
//    }
//
//    @GetMapping("/side/2")
//    public String side2() {
//        return "moistureNatrium/diet/side/2";
//    }
//
//    @GetMapping("/side/3")
//    public String side3() {
//        return "moistureNatrium/diet/side/3";
//    }
//
//    @GetMapping("/side/4")
//    public String side4() {
//        return "moistureNatrium/diet/side/4";
//    }
//
//    @GetMapping("/side/5")
//    public String side5() {
//        return "moistureNatrium/diet/side/5";
//    }
//
//    @GetMapping("/side/6")
//    public String side6() {
//        return "moistureNatrium/diet/side/6";
//    }
//
//    @GetMapping("/side/7")
//    public String side7() {
//        return "moistureNatrium/diet/side/7";
//    }
//
//    @GetMapping("/side/8")
//    public String side8() {
//        return "moistureNatrium/diet/side/8";
//    }
//
//    // 김치 상세
//    @GetMapping("/kimchi/1이")
//    public String kimchi1() {
//        return "moistureNatrium/diet/side/1";
//    }
//
//    @GetMapping("/kimchi/2")
//    public String kimchi2() {
//        return "moistureNatrium/diet/side/2";
//    }
//
//    @GetMapping("/kimchi/3")
//    public String kimchi3() {
//        return "moistureNatrium/diet/side/3";
//    }
//
//    @GetMapping("/kimchi/4")
//    public String kimchi4() {
//        return "moistureNatrium/diet/side/4";
//    }
//
//    @GetMapping("/kimchi/5")
//    public String kimchi5() {
//        return "moistureNatrium/diet/side/5";
//    }
//
//    @GetMapping("/kimchi/6")
//    public String kimchi6() {
//        return "moistureNatrium/diet/side/6";
//    }
//
//    @GetMapping("/kimchi/7")
//    public String kimchi7() {
//        return "moistureNatrium/diet/side/7";
//    }
//
//    @GetMapping("/kimchi/8")
//    public String kimchi8() {
//        return "moistureNatrium/diet/side/8";
//    }
}
