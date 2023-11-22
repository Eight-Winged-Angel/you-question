package cn.edu.cupk.learn.core.service;

import cn.edu.cupk.learn.common.domain.course.Practice;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.common.vo.course.CourseListVO;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.course.*;
import cn.edu.cupk.learn.common.vo.student.StudentListVO;
import cn.edu.cupk.learn.common.vo.notice.NoticeListVO;

public interface CourseService {

    /**
     * 创建课程
     *
     * @param title 课程题目
     * @param content 课程简介
     * @param teacherId   教师ID
     * @throws DbException 数据库出错
     */
    void addCourse(String title, String content, long teacherId) throws DbException;

    /**
     * 删除课程
     *
     * @param courseId  课程ID
     * @param teacherId 教师ID
     * @throws DbException 课程不存在
     */
    void deleteCourse(long courseId, long teacherId) throws DbException;

    /**
     * 更新课程信息
     *
     * @param courseId  课程id
     * @param title     课程标题
     * @param intro     课程简介
     * @param teacherId 本课程教师id
     * @throws DbException 数据库出错
     */
    void updateCourse(long courseId, String title, String intro, long teacherId) throws DbException;

    /**
     * 获取学生相关课程页
     *
     * @param studentId 学生ID
     * @return 课程简略信息页
     * @throws DbException 数据库出错
     */
    PageVO<CourseListVO> listStudentCourse(int page, long studentId) throws DbException;

    /**
     * 获取教师相关课程页
     *
     * @param teacherId 教师ID
     * @return 课程简略信息页
     * @throws DbException 常见数据库异常
     */
    PageVO<CourseListVO> listTeacherCourse(int page, long teacherId) throws DbException;


    /**
     * 获取课程详细信息
     *
     * @param courseId 课程id
     * @return 课程详细信息
     * @throws DbException 常见数据库异常
     */
    CourseVO getTeacherCourse(long courseId) throws DbException;

    /**
     * 仅比教师少邀请码
     *
     * @param courseId 课程id
     * @return 课程详细信息
     * @throws DbException 数据库出错
     */
    CourseVO getStudentCourse(long courseId) throws DbException;

    /**
     * 新发布通知
     * @param title 标题
     * @param content 内容
     * @param courseId 课程ID
     * @throws DbException 数据库出错
     */
    void addNotice(String title, String content, long courseId) throws DbException;

    /**
     * 获得课程所有通知
     *
     * @param courseId 课程id
     * @return 通知页
     * @throws DbException 常见数据库异常
     */
    PageVO<NoticeListVO> listNotice(int page, long courseId) throws DbException;

    /**
     * 学生添加课程
     *
     * @param studentId 学生ID
     * @throws ApiException 课程已添加
     */
    void addStudentCourse(long studentId, long courseId) throws ApiException;

    /**
     * 学生退课
     *
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @throws DbException 常见数据库异常
     */
    void deleteStudent(long studentId, long courseId) throws DbException;

    /**
     * 获取加入课程的学生名单
     *
     * @param keyWord  搜索关键字
     * @param courseId 课程id
     * @return 学生页
     * @throws DbException 常见数据库异常
     */
    PageVO<StudentListVO> listCourseStudent(int page, String keyWord, long courseId) throws DbException;

    /**
     * 根据邀请码返回课程详细信息
     *
     * @param inviteCode 邀请码
     * @return 课程详细信息
     * @throws DbException 常见数据库异常
     */
    CourseVO getCourseByInviteCode(String inviteCode) throws DbException;

    /**
     * 返回练习详细信息
     *
     * @param practiceId 练习ID
     * @return 练习信息
     * @throws DbException 常见数据库异常
     */
    Practice getPractice(long practiceId) throws DbException;

    /**
     * 返回练习简略页
     *
     * @param page     页数
     * @param courseId 课程id
     * @return 课程简略信息页，长度为10
     * @throws DbException 常见数据库异常
     */
    PageVO<Practice> listPractice(int page, long courseId) throws DbException;

    /**
     * 老师删除通知
     *
     * @param teacherId 老师id
     * @param courseId  课程id
     * @param noticeId  通知id
     * @throws DbException 常见数据库异常
     */
    void deleteNotice(long teacherId, long courseId, long noticeId) throws DbException;

    boolean verifyStudent(long studentId, long courseId) throws DbException;
}