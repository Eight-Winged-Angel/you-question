package cn.edu.cupk.learn.common.vo.course;

import cn.edu.cupk.learn.common.domain.course.Course;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseVO {

    private Long courseId;

    private String title;

    private String content;

    private String teacherName;

    private String schoolName;

    private Integer total;

    private String inviteCode;

    private Date startTime;

    private Date endTime;

    public CourseVO(@NotNull Course domain) {
        this.courseId = domain.getCourseId();
        this.title = domain.getTitle();
        this.content = domain.getContent();
        this.teacherName = domain.getTeacherName();
        this.schoolName = domain.getSchoolName();
        this.total = domain.getTotal();
        this.inviteCode = domain.getInviteCode();
        this.startTime = domain.getCreateTime();
        this.endTime = domain.getEndTime();
    }
}
