package cn.edu.cupk.learn.common.domain.course;

import cn.edu.cupk.learn.common.entity.course.CoursePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

/**
 * 课程信息
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
public class Course extends CoursePO {

    private static final long serialVersionUID = -5677886328125044163L;

    private Integer total;

    private String teacherName;

    private String schoolName;

}
