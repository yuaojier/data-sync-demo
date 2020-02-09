package aop.aspectj;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;

@Data
public class AroundContext<T> {
    /**
     * {@link AroundAspect#before(ProceedingJoinPoint, AroundContext)} 中返回值
     */
    private T beforeReturn;

    private T originReturn;

}
