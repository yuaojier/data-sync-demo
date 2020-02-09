package biz.exception;

/**
 * 远程业务结果未知异常
 */
public class RemoteBizUnknownException extends Exception {
    private static final long serialVersionUID = 4325091751134932310L;

    public RemoteBizUnknownException(String message) {
        super(message);
    }

    public RemoteBizUnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
