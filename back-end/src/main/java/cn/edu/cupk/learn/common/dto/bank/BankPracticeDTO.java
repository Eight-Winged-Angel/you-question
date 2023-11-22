package cn.edu.cupk.learn.common.dto.bank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 题库新练习表单
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BankPracticeDTO {

    @ApiModelProperty(value = "练习标题", required = true)
    @Length(min = 1, max = 255)
    private String title;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @Future
    private Date endTime;

    @ApiModelProperty("时间限制")
    @Pattern(regexp = "^([1-4]h)?(\\d{1,2}min)?(\\d{1,2}s)?$")
    private String timeLimit;

    @ApiModelProperty("练习说明")
    @Length(min = 1, max = 255)
    private String content;

    @ApiModelProperty(value = "题目与分值", required = true)
    private List<BankQuestionScore> question;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankQuestionScore {

        private Long id;

        private Short score;

    }
}
