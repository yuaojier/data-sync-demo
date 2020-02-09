package aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodLog {
    /**
     * 日志中输出参数名, 按顺序输出
     */
    String[] paramNames() default {};

    /**
     * 日志中输出参数索引, 优先级高于 {@link MethodLog#paramNames()}
     */
    int[] paramIndex() default {};

    /**
     * 是否输出方法结果, 默认true
     */
    boolean logReturn() default true;
}
