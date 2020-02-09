package biz.exception;

/**
 * 远程接口处理失败异常
 */
public class RemoteBizFailureException extends Exception {
    private static final long serialVersionUID = 2446116860776412442L;

    public RemoteBizFailureException(String message) {
        super(message);
    }

    public RemoteBizFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteBizFailureException(Throwable cause) {
        super(cause);
    }
}
