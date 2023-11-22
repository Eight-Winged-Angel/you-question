package cn.edu.cupk.learn.common.vo.bank;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.hutool.core.date.DateUtil;
import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankPracticeVO {

    private Long id;

    private String title;

    private String date;

    private String inviteCode;

    private Short count;

    private Integer score;

    private String content;

    private Long course;

    private Date startTime;

    private Date endTime;

    private Short seconds;

    public BankPracticeVO(@NotNull BankPractice domain) {
        this.id = domain.getPracticeId();
        this.title = domain.getTitle();
        this.date = DateUtil.date(domain.getUpdateTime()).toDateStr();
        this.inviteCode = domain.getInviteCode();
        this.count = domain.getCount();
        this.score = domain.getScore();
        this.content = domain.getContent();
        this.course = domain.getCourseId();
        this.startTime = domain.getStartTime();
        this.endTime = domain.getEndTime();
        this.seconds = domain.getTimeLimit();
    }

}
