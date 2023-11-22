package cn.edu.cupk.learn.common.entity.course;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.sql.Blob;

/**
 * t_course表
 * @author yangchen
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@With
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_course")
public class CoursePO implements Serializable {

    private static final long serialVersionUID = 3042112031982669233L;

    @TableId(type = IdType.AUTO)
    private Long courseId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("course_title")
    private String title;

    @TableField("course_content")
    private String content;

    @TableField
    private Long teacherId;

    @TableField
    private String inviteCode;

    @TableField
    private Date endTime;
}
