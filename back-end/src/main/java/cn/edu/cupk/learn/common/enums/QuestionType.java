package cn.edu.cupk.learn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 题型枚举
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Getter
@AllArgsConstructor
public enum QuestionType {

    UNKNOWN("未知题型", 0),
    SINGLE("单选题", 1),
    MULTIPLE("多选题", 2),
    JUDGE("判断题", 3),
    FILL("填空题", 4),
    APPLICATION("应用题", 5),

    ALL("", 100)
    ;

    @Contract(pure = true)
    public static QuestionType typeOf(@NotNull String type) {
        switch (type) {
            case "单选题":
                return SINGLE;
            case "多选题":
                return MULTIPLE;
            case "判断题":
                return JUDGE;
            case "填空题":
                return FILL;
            case "应用题":
                return APPLICATION;
            case "":
            case "所有":
            case "所有题":
            case "所有题型":
            case "不限制":
            case "无限制":
                return ALL;
            default:
                return UNKNOWN;
        }
    }

    @NotNull
    public static QuestionType valueOf(short value) {
        for (QuestionType type : QuestionType.values()) {
            if (type.value == value) {
                return type;
            }
        }

        throw new IllegalArgumentException();
    }

    private final String type;

    private int value;

    public short getValue() {
        return (short) value;
    }
}
