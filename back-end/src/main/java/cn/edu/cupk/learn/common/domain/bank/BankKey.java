package cn.edu.cupk.learn.common.domain.bank;

import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.hutool.core.convert.Convert;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 答案领域模型
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public abstract class BankKey {

    protected QuestionType type;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankOption {

        private String id;

        private String content;

        private boolean correct;

    }

    @Data
    public static class Single extends BankKey {

        List<BankOption> bankOptions;

        public Single(Object answer) {
            this.type = QuestionType.SINGLE;
            bankOptions = Convert.toList(BankOption.class, answer);
        }

        @Override
        public String toString() {
            return this.bankOptions.toString();
        }

        @Override
        public Object getAnswer() {
            return this.bankOptions;
        }
    }

    @Data
    public static class Multiple extends BankKey {

        List<BankOption> bankOptions;

        public Multiple(Object answer) {
            this.type = QuestionType.MULTIPLE;
            bankOptions = Convert.toList(BankOption.class, answer);
        }

        @Override
        public Object getAnswer() {
            return this.bankOptions;
        }
    }

    @Data
    public static class Judge extends BankKey {

        private boolean isCorrect;

        public Judge(Object answer) {
            this.type = QuestionType.JUDGE;
            isCorrect = Convert.toBool(answer, true);
        }

        @Override
        public Object getAnswer() {
            return this.isCorrect;
        }
    }

    @Data
    public static class Fill extends BankKey {

        private List<String> keys;

        public Fill(Object answer) {
            this.type = QuestionType.FILL;
            keys = Convert.toList(String.class, answer);
        }

        @Override
        public Object getAnswer() {
            return this.keys;
        }
    }

    @Data
    public static class Application extends BankKey {

        private String key;

        public Application(Object answer) {
            this.type = QuestionType.APPLICATION;
            key = Convert.toStr(answer, "");
        }

        @Override
        public Object getAnswer() {
            return this.key;
        }
    }

    public List<BankOption> getOptions() {
        return new ArrayList<>();
    }

    public boolean isCorrect() {
        return true;
    }

    public List<String> getKeys() {
        return new ArrayList<>();
    }

    public String getKey() {
        return "";
    }

    public abstract Object getAnswer();
}
