package cn.edu.cupk.learn.common.domain.course;

import cn.edu.cupk.learn.common.entity.KeyPO;
import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestion {

    private Long id;

    private String content;

    private Short type;

    private Short score;

    private String answer;

    private List<KeyPO> options;
}
