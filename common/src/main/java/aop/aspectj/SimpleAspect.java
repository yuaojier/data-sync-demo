package aop.aspectj;

public interface SimpleAspect {
    String POINTCUT = "pointcut()";

    void pointcut();
}
