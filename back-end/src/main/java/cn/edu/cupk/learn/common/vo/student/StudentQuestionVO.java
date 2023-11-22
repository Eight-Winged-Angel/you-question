package cn.edu.cupk.learn.common.vo.student;

import cn.edu.cupk.learn.common.entity.KeyPO;
import cn.hutool.core.util.StrUtil;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionVO {

    private Long id;

    private String content;

    private String type;

    private Short score;

    private Object answer;

    private List<OptionVO> options;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionVO {

        private String id;

        private String content;

        public OptionVO(@NotNull KeyPO key) {
            byte [] ch = "A".getBytes();
            ch[0] += key.getKeyId() - 1;
            this.id = StrUtil.str(ch, Charset.defaultCharset());
            this.content = key.getContent();
        }

    }
}
