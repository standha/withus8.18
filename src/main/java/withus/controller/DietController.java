package withus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diet")
public class DietController {
    // 리스트
    @GetMapping("/soup")
    public String soup() {
        return "moistureNatrium/diet/soup/list";
    }

    @GetMapping("/side")
    public String side() {
        return "moistureNatrium/diet/side/list";
    }

    @GetMapping("/kimchi")
    public String kimchi() {
        return "moistureNatrium/diet/kimchi/list";
    }

    // 국 상세
    @GetMapping("/soup/북엇국")
    public String soup1() {
        return "moistureNatrium/diet/soup/북엇국";
    }

    @GetMapping("/soup/들깨버섯찌개")
    public String soup2() {
        return "moistureNatrium/diet/soup/들깨버섯찌개";
    }

    @GetMapping("/soup/낙지감잣국")
    public String soup3() {
        return "moistureNatrium/diet/soup/낙지감잣국";
    }

    @GetMapping("/soup/소고기뭇국")
    public String soup4() {
        return "moistureNatrium/diet/soup/소고기뭇국";
    }

    @GetMapping("/soup/단호박배추된장국")
    public String soup5() {
        return "moistureNatrium/diet/soup/단호박배추된장국";
    }

    @GetMapping("/soup/바지락맑은국")
    public String soup6() {
        return "moistureNatrium/diet/soup/바지락맑은국";
    }

    @GetMapping("/soup/버섯순두부찌개")
    public String soup7() {
        return "moistureNatrium/diet/soup/버섯순두부찌개";
    }

    @GetMapping("/soup/닭곰탕")
    public String soup8() {
        return "moistureNatrium/diet/soup/닭곰탕";
    }

    // 반찬 상세
    @GetMapping("/side/감자카레볶음")
    public String side1() {
        return "moistureNatrium/diet/side/감자카레볶음";
    }

    @GetMapping("/side/뱅어포볶음")
    public String side2() {
        return "moistureNatrium/diet/side/뱅어포볶음";
    }

    @GetMapping("/side/다시다두부말이")
    public String side3() {
        return "moistureNatrium/diet/side/다시다두부말이";
    }

    @GetMapping("/side/황태조림")
    public String side4() {
        return "moistureNatrium/diet/side/황태조림";
    }

    @GetMapping("/side/브로콜리견과류볶음")
    public String side5() {
        return "moistureNatrium/diet/side/브로콜리견과류볶음";
    }

    @GetMapping("/side/느타리버섯볶음")
    public String side6() {
        return "moistureNatrium/diet/side/느타리버섯볶음";
    }

    @GetMapping("/side/소고기채소불고기")
    public String side7() {
        return "moistureNatrium/diet/side/소고기채소불고기";
    }

    @GetMapping("/side/시래기닭조림")
    public String side8() {
        return "moistureNatrium/diet/side/시래기닭조림";
    }

    // 김치 상세
    @GetMapping("/kimchi/상추겉절이")
    public String kimchi1() {
        return "moistureNatrium/diet/side/상추겉절이";
    }

    @GetMapping("/kimchi/오이생채")
    public String kimchi2() {
        return "moistureNatrium/diet/side/오이생채";
    }

    @GetMapping("/kimchi/참외겉절이")
    public String kimchi3() {
        return "moistureNatrium/diet/side/참외겉절이";
    }

    @GetMapping("/kimchi/채소피클")
    public String kimchi4() {
        return "moistureNatrium/diet/side/채소피클";
    }

    @GetMapping("/kimchi/고추김치")
    public String kimchi5() {
        return "moistureNatrium/diet/side/고추김치";
    }

    @GetMapping("/kimchi/콜라비깍두기")
    public String kimchi6() {
        return "moistureNatrium/diet/side/콜라비깍두기";
    }

    @GetMapping("/kimchi/청경채김치")
    public String kimchi7() {
        return "moistureNatrium/diet/side/청경채김치";
    }

    @GetMapping("/kimchi/저염겉절이")
    public String kimchi8() {
        return "moistureNatrium/diet/side/저염겉절이";
    }
}
