package cn.edu.cupk.learn.core.service;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.course.StudentQuestion;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.PageVO;

import java.util.Date;
import java.util.List;

public interface PracticeService {

    /**
     * 学生做题读题
     * @param practiceId 练习ID
     * @param studentId 学生ID
     * @return 题目数据
     */
    List<StudentQuestion> studentReadPractice(long practiceId, long studentId);

    /**
     * 学生提交答案
     * @param practiceId 练习ID
     * @param questionId 题目ID
     * @param studentId 学生ID
     * @param answer 答案
     */
    void studentSubmit(long practiceId, long questionId, long studentId, Object answer) throws DbException ;

    /**
     * 获取课程练习列表
     *
     * @param courseId 课程id
     * @param page 页码
     * @return 课程练习列表
     * @throws DbException 数据不合法
     */
    PageVO<BankPractice> listCoursePractice(long courseId, int page) throws DbException;

    /**
     * 邀请码增加课程练习
     * @param inviteCode 邀请码
     * @param title 标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param timeLimit 限时
     * @param content 说明
     * @param teacherId 教师ID
     * @throws DbException 数据不合法
     */
    void inviteCoursePractice(String inviteCode, String title, Date startTime, Date endTime, Short timeLimit,
                              String content, long teacherId, long courseId) throws DbException;
}
