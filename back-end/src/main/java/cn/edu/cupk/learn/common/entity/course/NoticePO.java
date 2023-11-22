package cn.edu.cupk.learn.common.entity.course;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_course_notice")
public class NoticePO implements Serializable{

    private static final long serialVersionUID = 3381675815708227320L;

    @TableId(type = IdType.AUTO)
    private Long noticeId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField
    private String noticeTitle;

    @TableField
    private String noticeContent;

    @TableField
    private Long courseId;
}
