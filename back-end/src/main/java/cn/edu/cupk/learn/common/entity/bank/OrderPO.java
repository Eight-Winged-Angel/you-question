package cn.edu.cupk.learn.common.entity.bank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 题号实体
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
@TableName("t_order")
public class OrderPO implements Serializable {

    private static final long serialVersionUID = 5715462475575435303L;

    @TableField
    private Long practiceId;

    @TableField("question_order")
    private Short order;

    @TableField("question_score")
    private Short score;

    @TableField
    private Long questionId;

}
