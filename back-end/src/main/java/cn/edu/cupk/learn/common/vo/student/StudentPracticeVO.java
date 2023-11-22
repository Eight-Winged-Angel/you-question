package cn.edu.cupk.learn.common.vo.student;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 学生端课程练习json
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPracticeVO {

    private Long id;

    private String title;

    private Short count;

    private Integer score;

    private String content;

    private Date startTime;

    private Date endTime;

    private Short seconds;

    public StudentPracticeVO(@NotNull BankPractice domain) {
        this.id = domain.getPracticeId();
        this.title = domain.getTitle();
        this.count = domain.getCount();
        this.score = domain.getScore();
        this.content = domain.getContent();
        this.startTime = domain.getStartTime();
        this.endTime = domain.getEndTime();
        this.seconds = domain.getTimeLimit();
    }

}
