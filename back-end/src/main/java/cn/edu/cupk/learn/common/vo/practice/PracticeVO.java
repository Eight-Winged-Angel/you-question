package cn.edu.cupk.learn.common.vo.practice;

import cn.edu.cupk.learn.common.domain.course.Practice;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 练习JSON
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
public class PracticeVO extends Practice {

    private String state;

    public PracticeVO(@NotNull Practice domain) {
        this.practiceId = domain.getPracticeId();
        this.title = domain.getTitle();
        this.startTime = domain.getStartTime();
        this.endTime = domain.getEndTime();
        this.timeLimit = domain.getTimeLimit();
        this.content = domain.getContent();
        Date now = new Date();
        if (this.endTime == null) {
            if (this.startTime == null) {
                this.state = "无限制";
                return;
            }

            if (this.startTime.before(now)) {
                this.state = "进行中";
                return;
            }

            this.state = "未开始";
            return;
        }

        if (this.endTime.before(now)) {
            this.state = "已结束";
            return;
        }

        if (this.startTime.before(now)) {
            this.state = "进行中";
            return;
        }

        this.state = "未开始";
    }

}
