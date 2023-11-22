package cn.edu.cupk.learn.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间转换
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TimeUtil {

    private static final Pattern PATTERN_HOUR = Pattern.compile("[1-4](?=h)");

    private static final Pattern PATTERN_MINUTE = Pattern.compile("\\d{1,2}(?=(min))");

    private static final Pattern PATTERN_SECOND = Pattern.compile("\\d{1,2}(?=s)");

    /**
     * 文本限时转为秒数，最高4h
     * 示例：2h30min
     * @param s 限时文本
     * @return 秒数
     */
    public Short toSeconds(String s) {
        if (s == null) {
            return null;
        }

        short res = 0;
        Matcher matcher = PATTERN_HOUR.matcher(s);
        if (matcher.find()) {
            res += Short.parseShort(matcher.group()) * 3600;
        }

        matcher = PATTERN_MINUTE.matcher(s);
        if (matcher.find()) {
            res += Short.parseShort(matcher.group()) * 60;
        }

        matcher = PATTERN_SECOND.matcher(s);
        if (matcher.find()) {
            res += Short.parseShort(matcher.group());
        }

        return res;
    }

}
