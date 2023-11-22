package cn.edu.cupk.learn.common.dto.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeDTO {

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("通知内容")
    private String content;

    @ApiModelProperty("课程ID")
    private Long courseId;

}
