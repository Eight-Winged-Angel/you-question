package cn.edu.cupk.learn.common.entity.course;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 学生提交答案实体
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
@TableName("t_question_submit")
public class SubmitPO {

    @TableField
    private Long questionId;

    @TableField
    private Long practiceId;

    @TableField
    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("answer_submit")
    private String answer;

    @TableField("answer_correct")
    private Boolean correct;

    @TableField("answer_score")
    private Short score;

}
