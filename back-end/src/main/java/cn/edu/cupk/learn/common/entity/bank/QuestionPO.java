package cn.edu.cupk.learn.common.entity.bank;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目实体
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_question")
public class QuestionPO implements Serializable {

    private static final long serialVersionUID = 4783709912948645787L;

    @TableId(type = IdType.AUTO)
    private Long questionId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("question_content")
    private String content;

    @TableField("question_type")
    private Short type;

    @TableField("teacher_id")
    private Long teacherId;

}
