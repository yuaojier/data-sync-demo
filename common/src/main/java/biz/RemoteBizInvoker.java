package biz;

import biz.exception.RemoteBizFailureException;
import biz.exception.RemoteBizUnknownException;
import cn.hutool.core.exceptions.ExceptionUtil;
import retry.Retryable;

/**
 * 抽取远程业务接口, 接口中只包含一种远程业务处理实现,
 * <p>
 * 远程业务接口由于存在结果未知的情况, 在未知情况下需轮询业务状态, 直至确定最终结果或轮询超时;
 * <p>
 * 若轮询能够确认最终结果(成功/失败)则以通知方式通知调用端; 若轮询超时(结果未知)则调用远程业务取消接口, 并以通知方式通知业务调用端;
 * <p>
 * 1. 调用远程业务接口
 * <p>
 * 2. 判断远程接口返回
 * <p>
 * 2.1 接口响应成功/失败 -> 同步方式返回调用端
 * <p>
 * 2.2 接口响应未知 -> 同步返回接口未知, 调用方等待最终结果通知 -> 3
 * <p>
 * 3.  发起轮询
 * <p>
 * 3.1  判断结果
 * <p>
 * 3.2 轮询结果成功/失败 -> 发送业务结果通知, 结束
 * <p>
 * 3.3 超过轮询次数,且结果未知 -> 4
 * <p>
 * (可选)4. 调用远程业务取消接口({@link RemoteBizCancelInvoker}) -> 发送业务取消通知
 * <p>
 * 业务取消接口同样属于业务接口, 依然需要以以上流程处理; 若取消接口不确定时, 需人工介入进行业务处理
 * <p>
 * <pre>
 * <code>
 *
 * class FooBizCancelInvoker implements RemoteBizInvoker<FooRequest, FooCancelResponse, FooBizCancelEvent> {
 *
 *      public FooCancelResponse invoke(FooRequest request)throws RemoteBizFailureException, RemoteBizUnknownException {
 *          //biz cancel code
 *      }
 * }
 *
 * class FooBizInvoker implements RemoteBizInvoker<FooRequest, FooResponse, FooBizEvent>, RemoteBizCancelInvoker<FooRequest> {
 *
 *      private FooBizCancelInvoker cancelInvoker;
 *
 *      public void cancel(FooRequest request) throws RemoteBizFailureException, RemoteBizUnknownException {
 *          cancelInvoker.invoke(request);
 *      }
 *
 *      public FooResponse invoke(FooRequest request)throws RemoteBizFailureException, RemoteBizUnknownException {
 *          //biz invoke code
 *      }
 * ...
 * }
 * </code>
 * </pre>
 *
 * @param <R> 远程接口请求参数
 * @param <S> 远程接口响应参数
 * @param <E> 业务结果通知类型
 */
public interface RemoteBizInvoker<R, S, E extends RemoteBizEvent<R, S>> extends Retryable<R, S> {
    /**
     * {@link #invoke(Object)} 方法名称
     */
    String INVOKE_METHOD = "invoke";

    /**
     * 调用远程业务接口
     * <p>
     * 关于异常: 若抛出{@link RemoteBizUnknownException}则自动触发轮询处理, 调用{@link #confirm(Object, int)}方法
     *
     * @param r
     * @return
     * @throws RemoteBizFailureException 判断远程接口处理失败时抛出
     * @throws RemoteBizUnknownException 判断远程接口处理结果未知时抛出
     */
    S invoke(R r) throws RemoteBizFailureException, RemoteBizUnknownException;

    /**
     * 业务处理确认接口, 具体业务中可能为重复调用{@link #invoke(Object)} 也可能查询, 视情况而定
     *
     * @param r
     * @param pollingTime 轮询次数
     * @return
     * @throws RemoteBizFailureException
     * @throws RemoteBizUnknownException
     */
    S confirm(R r, int pollingTime) throws RemoteBizFailureException, RemoteBizUnknownException;

    Class<E> getEventType();

    /**
     * 获取业务键, 需保证唯一
     *
     * @param r
     * @return
     */
    String getBizKey(R r);

    @Override
    default S retry(R input, int retryTimes) {
        try {
            return confirm(input, retryTimes);
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }
}
