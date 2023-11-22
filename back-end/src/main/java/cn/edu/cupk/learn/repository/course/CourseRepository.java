package cn.edu.cupk.learn.repository.course;

import cn.edu.cupk.learn.common.domain.course.Course;
import cn.edu.cupk.learn.common.domain.course.Practice;
import cn.edu.cupk.learn.common.vo.course.CourseListVO;
import cn.edu.cupk.learn.common.entity.bank.QuestionPO;
import cn.edu.cupk.learn.common.entity.course.CoursePO;
import cn.edu.cupk.learn.common.vo.student.StudentListVO;
import cn.edu.cupk.learn.common.vo.notice.NoticeListVO;
import cn.edu.cupk.learn.common.vo.practice.PracticeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Blob;

/**
 * 教师课程访问数据库
 *
 * @author 箬玉
 * @author zwl
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Mapper
@Repository
public interface CourseRepository extends BaseMapper<CoursePO>{

    Course selectDomainById(@Param("courseId") Long courseId);

    /**
     * 删除课程时删除所有加入本课程学生的加入信息
     * @param courseId 课程ID
     */
    void deleteAllStudent(@Param("courseId") Long courseId);

    /**
     * 更新课程
     * @param courseId 课程ID
     * @param title 课程名
     * @param content 课程简介
     */
    void updateCourse(
            @Param("courseId") Long courseId,
            @Param("title") String title,
            @Param("content") String content);

    /**
     * 教师获取课程列表
     * @param page 页码
     * @param teacherId 教师ID
     * @return 课程列表
     */
    IPage<CourseListVO> pageListByTeacher(IPage<CourseListVO> page, @Param("teacherId") Long teacherId);

    /**
     * 学生获取通知列表
     * @param page 页码
     * @param courseId 课程ID
     * @return 通知列表
     */
    IPage<NoticeListVO> selectAllNoticeList(IPage<NoticeListVO> page, @Param("courseId") Long courseId);

    /**
     * 获取课程学生列表
     * @param isId 学号/姓名关键字
     * @param page 页码
     * @param keyWord 关键字
     * @param courseId 课程ID
     * @return 学生列表
     */
    IPage<StudentListVO> pageStudent(@Param("isId") Boolean isId, @Param("page")IPage<StudentListVO> page, @Param("keyWord") String keyWord,
                                     @Param("courseId") Long courseId);

    /**
     * 学生加入课程
     * @param studentId 学生ID
     * @param courseId 课程ID
     */
    void insertStudent(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    /**
     * 学生退出课程
     * @param studentId 学生ID
     * @param courseId 课程ID
     */
    void deleteStudent(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    /**
     * 获取学生课程列表
     * @param page 页码
     * @param studentId 学生ID
     * @return 课程列表
     */
    IPage<CourseListVO> pageListByStudent(IPage<CourseListVO> page, @Param("studentId") Long studentId);

    /**
     * 邀请码搜索课程
     * @param inviteCode 邀请码
     * @return 课程信息
     */
    Course selectDomainByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 判断邀请码是否存在
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    Boolean existByInviteCode(String inviteCode);

    /**
     * 获取课程练习列表
     * @param page 页码
     * @param courseId 课程ID
     * @return 课程练习列表
     */
    IPage<Practice> pagePracticeList(IPage<PracticeVO> page, @Param("courseId") Long courseId);

    /**
     * 删除课程通知
     * @param courseId 课程ID
     * @param noticeId 通知ID
     */
    void deleteNotice(@Param("courseId") Long courseId, @Param("noticeId") Long noticeId);

    /**
     * 判断学生是否在课程中
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 是否在课程中
     */
    Boolean existsStudent(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

}
