package cn.edu.cupk.learn.common.dto.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Blob;

/**
 * 课程信息表单
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class CourseDTO {

    @ApiModelProperty("课程ID")
    private Long courseId;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程说明")
    private String content;

}
