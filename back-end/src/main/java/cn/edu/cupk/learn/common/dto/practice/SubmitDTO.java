package cn.edu.cupk.learn.common.dto.practice;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDTO {

    @ApiModelProperty("练习ID")
    private Long practiceId;

    @ApiModelProperty("题目ID")
    private Long questionId;

    @ApiModelProperty("答案")
    private Object answer;

}
