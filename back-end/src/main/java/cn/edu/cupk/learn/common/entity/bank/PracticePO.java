package cn.edu.cupk.learn.common.entity.bank;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 练习实体
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@With
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_practice")
public class PracticePO implements Serializable {

    private static final long serialVersionUID = 5715462475575425203L;

    @TableId(type = IdType.AUTO)
    private Long practiceId;

    @TableField("practice_title")
    private String title;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField
    private Date startTime;

    @TableField
    private Short timeLimit;

    @TableField
    private Date endTime;

    @TableField
    private Long courseId;

    @TableField
    private String inviteCode;

    @TableField
    private Long teacherId;

    @TableField("practice_content")
    private String content;

}
