package cn.edu.cupk.learn.common.vo.bank;

import lombok.*;

/**
 * 题目与参考答案JSON
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
public class BankQuestionVO {

    private Long id;

    private String type;

    private String content;

    private String date;

    // 前端用key会和保留字重合
    private Object answer;
}
