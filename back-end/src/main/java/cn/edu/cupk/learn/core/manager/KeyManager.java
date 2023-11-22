package cn.edu.cupk.learn.core.manager;

import cn.edu.cupk.learn.common.domain.bank.BankKey;
import cn.edu.cupk.learn.common.domain.bank.BankQuestion;
import cn.edu.cupk.learn.common.entity.KeyPO;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.bank.BankQuestionVO;
import cn.edu.cupk.learn.repository.bank.KeyRepository;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 答案Manager
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@AllArgsConstructor
public class KeyManager extends ServiceImpl<KeyRepository, KeyPO> {

    @NotNull
    @Contract("_, _ -> new")
    public BankKey getInstance(Object answer, @NotNull QuestionType type) throws DbException {
        switch (type) {
            case SINGLE:
                return new BankKey.Single(answer);
            case MULTIPLE:
                return new BankKey.Multiple(answer);
            case JUDGE:
                return new BankKey.Judge(answer);
            case FILL:
                return new BankKey.Fill(answer);
            case APPLICATION:
                return new BankKey.Application(answer);
            case UNKNOWN:
            default:
                throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }
    }

    @Transactional
    public void save(@NotNull BankKey bankKey, long questionId) throws DbException {
        List<KeyPO> keys;
        switch (bankKey.getType()) {
            case SINGLE:
            case MULTIPLE:
                keys = bankKey.getOptions().stream()
                        .map(item -> parseOptions(item, questionId)).collect(Collectors.toList());
                break;
            case JUDGE:
                keys = ListUtil.toList(
                        KeyPO.builder()
                                .questionId(questionId)
                                .keyId((short) 1)
                                .correct(bankKey.isCorrect()).build()
                );
                break;
            case FILL:
                keys = IntStream.range(0, bankKey.getKeys().size())
                        .mapToObj(i -> parseKeys(bankKey.getKeys().get(i), i, questionId))
                        .collect(Collectors.toList());
                break;
            case APPLICATION:
                keys = ListUtil.toList(
                        KeyPO.builder()
                                .questionId(questionId)
                                .keyId((short) 1)
                                .content(bankKey.getKey()).build()
                );
                break;
            case UNKNOWN:
            default:
                throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }

        saveBatch(keys);
    }

    public KeyPO parseOptions(@NotNull BankKey.BankOption bankOption, long questionId) throws DbException {
        if (bankOption.getId().length() != 1) {
            throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }

        char code = bankOption.getId().charAt(0);
        if (!CharUtil.isLetter(code)) {
            throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }

        // ASCII码选项对应：A/a-1，B/b-2，……
        code -= CharUtil.isLetterLower(code) ? 96 : 64;
        return KeyPO.builder()
                .questionId(questionId)
                .keyId((short) code)
                .content(bankOption.getContent())
                .correct(bankOption.isCorrect()).build();
    }

    public KeyPO parseKeys(String content, int index, long questionId) throws DbException {
        return KeyPO.builder()
                .questionId(questionId)
                .content(content)
                .keyId((short) (index + 1)).build();
    }

    public BankQuestionVO parseBankQuestionVO(@NotNull BankQuestion domain) {
        QuestionType type = QuestionType.valueOf(domain.getType());
        Object answer;
        switch (type) {
            case SINGLE:
            case MULTIPLE:
                answer = domain.getKeys().stream()
                        .map(this::parseOption).collect(Collectors.toList());
                break;
            case JUDGE:
                answer = domain.getKeys().get(0).getCorrect();
                break;
            case FILL:
                answer = domain.getKeys().stream()
                        .map(KeyPO::getContent).collect(Collectors.toList());
                break;
            case APPLICATION:
                answer = domain.getKeys().get(0).getContent();
                break;
            case UNKNOWN:
            default:
                throw new DbException(DbCode.DATA_NOT_SUPPORT);
        }

        return BankQuestionVO.builder()
                .id(domain.getId())
                .type(type.getType())
                .content(domain.getContent())
                .date(DateUtil.date(domain.getDate()).toDateStr())
                .answer(getInstance(answer, type).getAnswer()).build();
    }

    public BankKey.BankOption parseOption(@NotNull KeyPO po) {
        byte [] ch = "A".getBytes();
        ch[0] += po.getKeyId() - 1;
        return BankKey.BankOption.builder()
                .id(StrUtil.str(ch, Charset.defaultCharset()))
                .content(po.getContent())
                .correct(po.getCorrect()).build();
    }
}
