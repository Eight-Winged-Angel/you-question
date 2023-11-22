package cn.edu.cupk.learn.core.impl;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.course.StudentQuestion;
import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import cn.edu.cupk.learn.common.entity.bank.QuestionPO;
import cn.edu.cupk.learn.common.entity.course.SubmitPO;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.core.manager.PracticeManager;
import cn.edu.cupk.learn.core.manager.SubmitManager;
import cn.edu.cupk.learn.core.service.PracticeService;
import cn.edu.cupk.learn.repository.bank.OrderRepository;
import cn.edu.cupk.learn.repository.bank.PracticeRepository;
import cn.edu.cupk.learn.repository.bank.QuestionRepository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Genghe, Icy
 * @version 1.0.0
 * @apiNote 做题模块
 * @since 1.0.0
 */

@Service
@AllArgsConstructor
public class PracticeServiceImpl implements PracticeService {

    private final PracticeRepository practiceRepository;

    private final QuestionRepository questionRepository;

    private final OrderRepository orderRepository;

    private final PracticeManager practiceManager;

    private final SubmitManager submitManager;

    @Override
    public List<StudentQuestion> studentReadPractice(long practiceId, long studentId) {
        return practiceRepository.selectQuestionStudent(practiceId, studentId);
    }

    @Override
    public void studentSubmit(long practiceId, long questionId, long studentId, Object answer) throws DbException {
        if (!submitManager.access2Submit(practiceId, questionId, studentId)) {
            throw new DbException(DbCode.STUDENT_NOT_FOUND);
        }

        QuestionPO question = questionRepository.selectById(questionId);
        if (question == null) {
            throw new DbException(DbCode.QUESTION_NOT_FOUND);
        }

        String strAnswer = practiceManager.answer2String(QuestionType.valueOf(question.getType()), answer);
        SubmitPO submit = SubmitPO.builder()
                .practiceId(practiceId)
                .questionId(questionId)
                .userId(studentId)
                .answer(strAnswer).build();
        submitManager.submit(submit);
    }

    @Override
    public PageVO<BankPractice> listCoursePractice(long courseId, int page) throws DbException {
        IPage<BankPractice> res = practiceRepository.pageByCourse(new Page<>(page, 10), courseId);
        if (page > res.getPages()) {
            res = practiceRepository.pageByCourse(new Page<>(res.getPages(), 10), courseId);
        }
        return new PageVO<>(res);
    }

    @Override
    @Transactional
    public void inviteCoursePractice(String inviteCode, String title, Date startTime, Date endTime, Short timeLimit,
                                     String content, long teacherId, long courseId) throws DbException {
        PracticePO practice = practiceRepository.selectByInviteCode(inviteCode);
        if (practice == null) {
            throw new DbException(DbCode.INVITE_CODE_NOT_FOUND);
        }

        if (practice.getCourseId() == null) {
            practice.setCourseId(courseId);
            practiceRepository.updateById(practice);
            return;
        }

        PracticePO newPractice = practiceManager.generatePracticePO(
                title == null ? practice.getTitle() : title, startTime, endTime, timeLimit,
                content == null ? practice.getContent() : content, teacherId);
        newPractice.setCourseId(courseId);
        practiceRepository.insert(newPractice);
        orderRepository.copy(newPractice.getPracticeId(), practice.getPracticeId());
    }
}
