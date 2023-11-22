package cn.edu.cupk.learn.common.domain.bank;

import cn.edu.cupk.learn.common.entity.KeyPO;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目带参考答案领域模型
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
public class BankQuestion implements Serializable {

    private static final long serialVersionUID = 4192873408617718827L;

    private Long id;

    private Short type;

    private String content;

    private Date date;

    private List<KeyPO> keys;

}
