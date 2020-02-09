package biz;


import biz.exception.RemoteBizFailureException;
import biz.exception.RemoteBizUnknownException;

/**
 * 远程业务取消接口, 若取消接口需重试, 请实现{@link RemoteBizInvoker} 接口,
 */
public interface RemoteBizCancelInvoker<R> {
    void cancel(R request) throws RemoteBizFailureException, RemoteBizUnknownException;
}
