package cn.edu.cupk.learn.core.manager;

import cn.edu.cupk.learn.common.entity.course.CoursePO;
import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.repository.course.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用户具体权限校验Manager
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@AllArgsConstructor
public class AuthManager {

    private final CourseRepository courseRepository;

    /**
     * 判断课程是否存在且操作者是否为本课程创建者
     *
     * @param courseId  课程id
     * @param teacherId 老师id
     * @throws ApiException 校验失败
     */
    public void checkTeacher(long courseId, long teacherId) throws ApiException {
        CoursePO coursePO = courseRepository.selectById(courseId);
        if (coursePO == null) {
            throw new DbException(DbCode.COURSE_NOT_EXIST);
        }

        if (coursePO.getTeacherId() != teacherId) {
            throw new ApiException(ApiCode.WRONG_CREATOR);
        }
    }

}
