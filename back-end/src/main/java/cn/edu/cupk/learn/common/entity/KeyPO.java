package cn.edu.cupk.learn.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 答案实体
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_question_key")
public class KeyPO implements Serializable {

    private static final long serialVersionUID = 9117947918115257855L;

    @TableField("question_id")
    private Long questionId;

    @TableField("key_id")
    private Short keyId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("answer_content")
    private String content;

    @TableField
    private Boolean correct;
}
