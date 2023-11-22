package cn.edu.cupk.learn.core.impl;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.bank.BankKey;
import cn.edu.cupk.learn.common.domain.bank.BankQuestion;
import cn.edu.cupk.learn.common.dto.bank.BankPracticeDTO;
import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import cn.edu.cupk.learn.common.entity.bank.QuestionPO;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.core.manager.KeyManager;
import cn.edu.cupk.learn.core.manager.PracticeManager;
import cn.edu.cupk.learn.core.service.BankService;
import cn.edu.cupk.learn.repository.bank.PracticeRepository;
import cn.edu.cupk.learn.repository.bank.QuestionRepository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 题库服务实现
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private final QuestionRepository questionRepository;

    private final PracticeRepository practiceRepository;

    private final KeyManager keyManager;

    private final PracticeManager practiceManager;

    @Override
    @Transactional
    public void addBankQuestion(@NotNull QuestionType type, String content, Object answer, long teacherId) throws DbException {
        try {
            BankKey bankKey = keyManager.getInstance(answer, type);
            QuestionPO question = QuestionPO.builder()
                    .type(type.getValue())
                    .content(content)
                    .teacherId(teacherId)
                    .build();
            questionRepository.insert(question);
            keyManager.save(bankKey, question.getQuestionId());
        } catch (Exception e) {
            throw new DbException(DbCode.DATA_NOT_SUPPORT, e.getMessage());
        }
    }

    @Override
    public PageVO<BankQuestion> listBankQuestion(String keyWord, QuestionType type, Long teacherId, int page) throws DbException {
        if (type == QuestionType.UNKNOWN) {
            throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }

        IPage<BankQuestion> res = questionRepository.pageWithKey(new Page<>(page, 10), keyWord, type.getValue(), teacherId);
        if (page > res.getPages()) {
            res = questionRepository.pageWithKey(new Page<>(res.getPages(), 10), keyWord, type.getValue(), teacherId);
        }
        return new PageVO<>(res);
    }

    @Override
    @Transactional
    public String addBankPractice(
            String title, Date startTime, Date endTime, Short timeLimit, String content, long teacherId,
            List<BankPracticeDTO.BankQuestionScore> question) throws DbException {
        PracticePO practice = practiceManager.generatePracticePO(title, startTime, endTime, timeLimit, content, teacherId);
        practiceRepository.insert(practice);
        practiceManager.saveOrder(question, practice.getPracticeId());
        return practice.getInviteCode();
    }

    @Override
    public PageVO<BankPractice> listBankPractice(String keyWord, int page) throws DbException {
        IPage<BankPractice> res = practiceRepository.selectAllWithIntro(new Page<>(page, 10), keyWord);
        if (page > res.getPages()) {
            res = practiceRepository.selectAllWithIntro(new Page<>(res.getPages(), 10), keyWord);
        }
        return new PageVO<>(res);
    }

    @Override
    @NotNull
    public BankPractice inviteBankPractice(String inviteCode) throws DbException {
        BankPractice practice = practiceRepository.selectByInviteWithIntro(inviteCode);
        if (practice == null) {
            throw new DbException(DbCode.INVITE_CODE_NOT_FOUND);
        }

        return practice;
    }
}
