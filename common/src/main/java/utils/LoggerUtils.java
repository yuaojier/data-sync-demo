package utils;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

/**
 * 统一异常日志格式工具类, 按照logstash分割格式输出异常日志
 * @author maxh
 * @since 2.0, 2018/3/10 9:45.
 */
public class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    /**
     * 异常信息描述属性名
     */
    private static final String MSG_KEY_NAME = "errMsg";

    /**
     * 异常堆栈标记
     */
    private static final String STACK_INFO_TAG = "stack info = ";

    /**
     * value占位符
     * <p>
     * 如: key = {}
     */
    private static final String VALUE_PLACEHOLDER = "{}";

    /**
     * key value 分隔符
     * <p>
     * 如: key1 = {}
     */
    private static final String KEY_VALUE_SEPARATOR = " = ";

    /**
     * key 分隔符
     * <p>
     * 如: key1 = {}, key2 = {}
     */
    private static final String KEY_SEPARATOR = ", ";

    /**
     * 默认key name
     */
    private static final String DEFAULT_KEY_NAME = "KEY";

    /**
     * 使用默认{@link LoggerUtils#logger}输出异常信息
     *
     * @param typeDesc 异常类型描述
     */
    public static void error(String typeDesc, Throwable throwable, Object... values) {
        error(logger, typeDesc, throwable, values);
    }

    public static void info(String typeDesc, Object... values) {
        info(logger, typeDesc, values);
    }

    public static void info(Logger logger, String typeDesc, Object... values) {
        info(logger, typeDesc, null, null, values);
    }

    public static void info(String typeDesc, String[] keys, Object... values) {
        info(logger, typeDesc, keys, values);
    }

    public static void info(Logger logger, String typeDesc, String[] keys, Object... values) {
        log(logger, Level.INFO, typeDesc, null, keys, values);
    }

    /**
     * 使用默认{@link LoggerUtils#logger}输出异常信息, 并指定日志中附加参数的key
     *
     * @param keys     附加参数key
     * @param values   附加参数
     * @param typeDesc 异常类型描述
     */
    public static void error(String typeDesc, Throwable throwable, String[] keys, Object... values) {
        error(logger, typeDesc, throwable, keys, values);
    }

    /**
     * 使用默认参数名, key1, key2
     *
     * @param logger   指定外部logger
     * @param typeDesc 异常类型
     * @param values   其他参数
     */
    public static void error(Logger logger, String typeDesc, Throwable throwable, Object... values) {
        error(logger, typeDesc, throwable, null, values);
    }

    /**
     * 输出异常日志并附加其他运行时参数, 如具体接口调用参数
     * <p>
     * 如果keys.length < values.length 则根据长度生成默认key
     * <p>
     * 最终日志格式为:
     * <p>
     * {typeDesc}, key1 = {v1}, key2 = {v2},  errMsg = {throwable.getMessage}, stack info =
     * <br/>
     * 异常堆栈
     * </p>
     * 如:
     * <p>异常类型, KEY0 = v0, KEY1 = v1, errMsg = 测试异常, stack info =
     * <br/>
     * java.lang.Exception: 测试异常
     * </p>
     *
     * @param logger   指定外部logger
     * @param typeDesc 异常类型
     * @param keys     其他参数key
     * @param values   其他参数
     */
    public static void error(Logger logger, String typeDesc, Throwable throwable, String[] keys, Object... values) {
        log(logger, Level.ERROR, typeDesc, throwable, keys, values);
    }

    public static void log(Logger logger, Level level, String typeDesc, Throwable throwable, String[] keys, Object... values) {
        if (isLevelEnable(logger, level)) {

            int keyLen = ArrayUtils.getLength(keys);
            int valLen = ArrayUtils.getLength(values);
            if (valLen > keyLen) {
                keys = ArrayUtils.addAll(keys, generateKeys(keyLen, valLen - keyLen));
            }
            StringBuilder formatBuilder = buildFormat(keys);
            values = ArrayUtils.addAll(new Object[]{typeDesc}, ArrayUtils.toArray(values));
            StringBuilder msgBuilder = format(formatBuilder.toString(), values);
            if (level == Level.ERROR) {
                if (throwable != null) {
                    msgBuilder.append(MSG_KEY_NAME).append(KEY_VALUE_SEPARATOR).append(ExceptionUtils.getRootCauseMessage(throwable)).append(KEY_SEPARATOR).append(STACK_INFO_TAG);
                    logger.error(msgBuilder.toString(), throwable);
                } else {
                    logger.error(msgBuilder.toString());
                }
            } else {
                invokeLog(logger, level, msgBuilder.toString());
            }
        }
    }

    /**
     * 生成默认key
     *
     * @param startIndex 默认key开始索引值
     * @param count      生成数量
     */
    private static String[] generateKeys(int startIndex, int count) {
        List<String> newKeys = Lists.newArrayList();
        for (int i = startIndex; i < startIndex + count; i++) {
            newKeys.add(DEFAULT_KEY_NAME + i);
        }
        return newKeys.toArray(new String[]{});
    }

    /**
     * 根据keys生成日志format
     *
     * @param keys 附加参数keys
     */
    private static StringBuilder buildFormat(String... keys) {
        StringBuilder sb = new StringBuilder(VALUE_PLACEHOLDER).append(KEY_SEPARATOR);
        if (keys != null) {
            Stream.of(keys).forEach(s -> sb.append(s).append(KEY_VALUE_SEPARATOR).append(VALUE_PLACEHOLDER).append(KEY_SEPARATOR));
            return sb;
        }
        return sb;
    }

    /**
     * 重写{@link StrFormatter#format(String, Object...)}, 修改返回类型为{@link StringBuilder}
     */
    private static StringBuilder format(final String strPattern, final Object... argArray) {
        if (StrUtil.isBlank(strPattern) || ArrayUtil.isEmpty(argArray)) {
            return new StringBuilder();
        }
        final int strPatternLength = strPattern.length();

        /*
         * 初始化定义好的长度以获得更好的性能
         */
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        /*
         * 记录已经处理到的位置
         */
        int handledPosition = 0;

        /*
        占位符所在位置
         */
        int delimIndex;
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(StrUtil.EMPTY_JSON, handledPosition);
            /*
            剩余部分无占位符
             */
            if (delimIndex == -1) {
                /*
                不带占位符的模板直接返回
                 */
                if (handledPosition == 0) {
                    return new StringBuilder(strPattern);
                } else { //字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf;
                }
            } else {
                /*
                转义符
                 */
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == StrUtil.C_BACKSLASH) {
                    /*
                     * 双转义符, 转义符之前还有一个转义符，占位符依旧有效
                     */
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == StrUtil.C_BACKSLASH) {
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(StrUtil.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        //占位符被转义
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(StrUtil.C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                } else {//正常占位符
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(StrUtil.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // append the characters following the last {} pair.
        //加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf;
    }

    private static boolean isLevelEnable(Logger logger, Level level) {
        try {
            return (boolean) MethodUtils.invokeMethod(logger, StrFormatter.format("is{}Enabled", StrUtil.upperFirst(level.name().toLowerCase())));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logReflectException("判读日志级别异常", e);
        }
        return false;
    }

    private static void invokeLog(Logger logger, Level level, String msg) {
        try {
            MethodUtils.invokeMethod(logger, level.name().toLowerCase(), msg);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logReflectException("动态级别输出日志异常", e);
        }
    }

    private static void logReflectException(String type, Throwable throwable) {
        LoggerUtils.logger.error(StrFormatter.format("{}, {} = {}, {}", type, MSG_KEY_NAME, throwable.getMessage(), STACK_INFO_TAG), throwable);
    }
}
