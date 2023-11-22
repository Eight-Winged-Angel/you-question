package cn.edu.cupk.learn.common.dto.practice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 邀请码添加练习表单
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class PracticeInviteDTO {

    @ApiModelProperty(value = "练习邀请码", required = true)
    private String inviteCode;

    @ApiModelProperty(value = "课程ID", required = true)
    private Long courseId;

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
}
