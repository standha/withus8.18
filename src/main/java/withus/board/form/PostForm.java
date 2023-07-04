package withus.board.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostForm {
    @NotEmpty(message="제목은 필수 항목입니다,")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "카테고리는 필수 항목입니다.")
    private String category;
}
