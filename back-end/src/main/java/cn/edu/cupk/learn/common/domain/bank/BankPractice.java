package cn.edu.cupk.learn.common.domain.bank;

import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 题库练习领域模型
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
public class BankPractice extends PracticePO {

    private static final long serialVersionUID = 338354908275191566L;

    private Short count;

    private Integer score;

}
