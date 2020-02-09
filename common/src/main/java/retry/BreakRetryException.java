package retry;

/**
 * 抛出异常时, 不再重试
 *
 */
public class BreakRetryException extends RuntimeException {
    private static final long serialVersionUID = -4876575634691789855L;
}
