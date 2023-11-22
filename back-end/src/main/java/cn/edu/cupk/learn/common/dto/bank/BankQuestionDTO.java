package cn.edu.cupk.learn.common.dto.bank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * 题库新题目表单
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BankQuestionDTO {

    @ApiModelProperty(value = "题型", required = true)
    @Pattern(regexp = "^(([单多]选)|(判断)|(填空)|(应用))题$")
    private String type;

    @ApiModelProperty(value = "题干", required = true)
    @Length(min = 1, max = 255)
    private String content;

    @ApiModelProperty(value = "答案", required = true)
    private Object answer;
}
