package cn.edu.cupk.learn.core.manager;

import cn.edu.cupk.learn.common.domain.course.StudentQuestion;
import cn.edu.cupk.learn.common.dto.bank.BankPracticeDTO;
import cn.edu.cupk.learn.common.entity.bank.OrderPO;
import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.student.StudentQuestionVO;
import cn.edu.cupk.learn.repository.bank.OrderRepository;
import cn.edu.cupk.learn.repository.bank.PracticeRepository;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 题号Manager
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@AllArgsConstructor
public class PracticeManager extends ServiceImpl<OrderRepository, OrderPO> {

    private final PracticeRepository practiceRepository;

    public String answer2String(@NotNull QuestionType type, Object answer) throws DbException {
        switch (type) {
            case SINGLE:
            case MULTIPLE:
                return String.join("", Convert.toList(String.class, answer));
            case JUDGE:
                return Convert.toBool(answer).toString();
            case FILL:
                return String.join("||", Convert.toList(String.class, answer));
            case APPLICATION:
                return answer.toString();
            case UNKNOWN:
            default:
                throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }
    }

    public PracticePO generatePracticePO(String title, Date startTime, Date endTime, Short timeLimit,
                                         String content, long teacherId) throws DbException {
        String inviteCode = IdUtil.nanoId(12);
        while (practiceRepository.existsInviteCode(inviteCode)) {
            inviteCode = IdUtil.nanoId(12);
        }

        return PracticePO.builder()
                .title(title)
                .startTime(startTime)
                .timeLimit(timeLimit)
                .endTime(endTime)
                .teacherId(teacherId)
                .inviteCode(inviteCode)
                .content(content).build();
    }

    @Transactional
    public void saveOrder(@NotNull List<BankPracticeDTO.BankQuestionScore> question, long practiceId) {
        List<OrderPO> orders = IntStream.range(0, question.size())
                .mapToObj(i -> parseOrder(question.get(i), i, practiceId)).collect(Collectors.toList());
        saveBatch(orders);
    }

    public OrderPO parseOrder(@NotNull BankPracticeDTO.BankQuestionScore question, int index, long practiceId) {
        return OrderPO.builder()
                .practiceId(practiceId)
                .order((short) (index + 1))
                .score(question.getScore())
                .questionId(question.getId()).build();
    }

    public StudentQuestionVO parseStudentQuestionVO(@NotNull StudentQuestion domain) {
        QuestionType type = QuestionType.valueOf(domain.getType());
        Object answer = null;
        List<StudentQuestionVO.OptionVO> options = null;
        if (type == QuestionType.SINGLE || type == QuestionType.MULTIPLE) {
            options = domain.getOptions().stream().map(StudentQuestionVO.OptionVO::new)
                    .collect(Collectors.toList());
        }
        if (domain.getAnswer() != null) {
            switch (type) {
                case SINGLE:
                case MULTIPLE:
                    answer = domain.getAnswer().chars().mapToObj(ch -> CharUtil.toString((char) ch))
                            .collect(Collectors.toList());
                    break;
                case JUDGE:
                    answer = "true".equals(domain.getAnswer());
                    break;
                case FILL:
                    answer = Arrays.stream(domain.getAnswer().split("\\|\\|")).collect(Collectors.toList());
                    break;
                case APPLICATION:
                    answer = domain.getAnswer();
                    break;
                case UNKNOWN:
                default:
                    throw new DbException(DbCode.DATA_NOT_SUPPORT);
            }
        }

        return StudentQuestionVO.builder()
                .id(domain.getId())
                .type(type.getType())
                .score(domain.getScore())
                .content(domain.getContent())
                .answer(answer)
                .options(options).build();
    }

}
