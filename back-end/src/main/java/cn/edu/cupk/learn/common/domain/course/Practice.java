package cn.edu.cupk.learn.common.domain.course;

import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 练习信息
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
public class Practice {

    protected Long practiceId;

    protected String title;

    protected Date startTime;

    protected Short timeLimit;

    protected Date endTime;

    protected String content;

    public Practice(@NotNull PracticePO po) {
        this.practiceId = po.getPracticeId();
        this.title = po.getTitle();
        this.startTime = po.getStartTime();
        this.timeLimit = po.getTimeLimit();
        this.endTime = po.getEndTime();
        this.content = po.getContent();
    }
}
