package cn.edu.cupk.learn.common.vo.course;

import lombok.Data;

/**
 * 课程列表简略信息
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class CourseListVO {

    private long courseId;

    private String title;

    private String content;

    private String teacher;
}
