package cn.edu.cupk.learn.core.service;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.bank.BankQuestion;
import cn.edu.cupk.learn.common.dto.bank.BankPracticeDTO;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.PageVO;

import java.util.Date;
import java.util.List;

/**
 * 题库相关服务
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BankService {

    /**
     * 创建新题目
     * @param type 题型
     * @param content 题干
     * @param answer 答案
     * @param teacherId 教师ID
     */
    void addBankQuestion(QuestionType type, String content, Object answer, long teacherId) throws DbException;

    /**
     * 查找题库题目页
     * @param keyWord 关键字
     * @param type 题型
     * @param teacherId 教师ID
     * @param page 页码
     * @return 题库题目页
     * @throws DbException 数据类型出错
     */
    PageVO<BankQuestion> listBankQuestion(String keyWord, QuestionType type, Long teacherId, int page) throws DbException;

    /**
     * 创建新练习
     * @param title 练习标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param timeLimit 时间限制（秒数）
     * @param content 练习说明
     * @param question 题目分值列表
     * @return 邀请码
     * @throws DbException 数据类型出错
     */
    String addBankPractice(String title, Date startTime, Date endTime, Short timeLimit, String content,
                           long teacherId, List<BankPracticeDTO.BankQuestionScore> question) throws DbException;

    /**
     * 查找题库练习页
     * @param keyWord 关键字
     * @param page 页码
     * @return 题库练习页
     * @throws DbException 数据类型出错
     */
    PageVO<BankPractice> listBankPractice(String keyWord, int page) throws DbException;

    /**
     * 查找练习信息
     * @param inviteCode 邀请码
     * @return 练习信息
     * @throws DbException 练习不存在
     */
    BankPractice inviteBankPractice(String inviteCode) throws DbException;
}
