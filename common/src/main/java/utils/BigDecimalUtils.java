package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static <T extends Number> BigDecimal add(T value1, T value2) {
        return BigDecimalUtils.add(value1, value2, 2);
    }

    public static <T extends Number> BigDecimal add(T value1, T value2, int scale) {
        return BigDecimalUtils.add(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static <T extends Number> BigDecimal add(T value1, T value2, int scale, int roundingMode) {
        BigDecimal b1 = new BigDecimal(value1.doubleValue());
        BigDecimal b2 = new BigDecimal(value2.doubleValue());
        return b1.add(b2).setScale(scale, roundingMode);
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static <T extends Number> BigDecimal sub(T value1, T value2) {
        return BigDecimalUtils.sub(value1, value2, 2);
    }

    public static <T extends Number> BigDecimal sub(T value1, T value2, int scale) {
        return BigDecimalUtils.sub(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static <T extends Number> BigDecimal sub(T value1, T value2, int scale, int roundingMode) {
        BigDecimal b1 = new BigDecimal(value1.doubleValue());
        BigDecimal b2 = new BigDecimal(value2.doubleValue());
        return b1.subtract(b2).setScale(scale, roundingMode);
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static <T extends Number> BigDecimal mul(T value1, T value2) {
        return BigDecimalUtils.mul(value1, value2, 2);
    }

    public static <T extends Number> BigDecimal mul(T value1, T value2, int scale) {
        return BigDecimalUtils.mul(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static <T extends Number> BigDecimal mul(T value1, T value2, int scale, int roundingMode) {
        BigDecimal b1 = new BigDecimal(value1.doubleValue());
        BigDecimal b2 = new BigDecimal(value2.doubleValue());
        return b1.multiply(b2).setScale(scale, roundingMode);
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static <T extends Number> BigDecimal div(T value1, T value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(value1.doubleValue());
        BigDecimal b2 = new BigDecimal(value2.doubleValue());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留两位小数,四舍五入
     *
     * @param number 数值
     * @return 四舍五入后的字符串
     */
    public static String formatHalfUp(Number number) {
        return formatHalfUp(number, 2);
    }

    /**
     * 四舍五入
     *
     * @param number 数值
     * @param scale  精度
     * @return 四舍五入后的字符串
     */
    public static String formatHalfUp(Number number, int scale) {
        BigDecimal bg = new BigDecimal(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        return bg.toString();
    }
}