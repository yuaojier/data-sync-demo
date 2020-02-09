package thread;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class FunctionRunnable implements Runnable {

    /**
     * 执行的对象
     */
    private Object function;

    /**
     * 待执行的方法
     */
    private Method method;

    /**
     * 参数列表
     */
    private Object[] paramList;

    /**
     * 返回值对应name
     */
    private ResultType name;

    /**
     * 返回值包装结果
     */
    private Map<ResultType, Object> result;

    /**
     * 需要本线程执行后才能执行的线程
     * <p>
     * 这里不考虑下行线程需要串行
     * </p>
     */
    private FunctionRunnable[] functionRunnables;
    /**
     * 在串行时使用
     * 在上一个线程执行完成时，本线程需要将上一个线程执行后的结构替换到本线程中使用
     * 该值为替换参数的位置
     */
    private int replaceParamIndex = -1;

    private ChildThreadException childThreadException;

    @Builder
    public FunctionRunnable(ResultType name, Map<ResultType, Object> result, Object function, Method method, Object... paramList) {
        this.function = function;
        this.method = method;
        this.paramList = paramList;
        this.name = name;
        this.result = result;
    }

    @Builder
    public FunctionRunnable(ResultType name, Map<ResultType, Object> result, Object function, String methodName, Object... paramList) {
        this.function = function;
        this.method = ClassUtil.getDeclaredMethod(function.getClass(), methodName, getParamClass(paramList));
        this.paramList = paramList;
        this.name = name;
        this.result = result;
    }

    public void setFunctionRunnable(FunctionRunnable... functionRunnables) {
        this.functionRunnables = functionRunnables;
    }

    private Class[] getParamClass(Object[] paramList) {
        Class[] classes = null;

        if (ArrayUtil.isNotEmpty(paramList)) {
            classes = new Class[paramList.length];
            for (int i = 0; i < paramList.length; i++) {
                classes[i] = paramList[i].getClass();
            }
        }
        return classes;
    }

    @Override
    public void run() {
        Object invoke = null;
        boolean exception = false;
        try {
            invoke = method.invoke(function, paramList);
        } catch (IllegalAccessException | InvocationTargetException e) {
            exception = true;

            if (e instanceof InvocationTargetException) {
                Exception target = (Exception) ReflectUtil.getFieldValue(e, "target");
                childThreadException.addException(target);
            } else {

                childThreadException.addException(e);
            }
            log.error("执行方法错误,class:{} method:{}", function.getClass(), method.getName(), e);
        }
        result.put(name, invoke);

        // 执行串行的操作
        if (!exception && ArrayUtil.isNotEmpty(functionRunnables)) {
            for (FunctionRunnable functionRunnable : functionRunnables) {
                // 当该线程需要上一个线程结果去执行时，需要替换参数操作
                functionRunnable.setChildThreadException(childThreadException);
                Object[] paramList = functionRunnable.getParamList();
                if (functionRunnable.getReplaceParamIndex() > 0 && ArrayUtil.isNotEmpty(paramList)) {
                    paramList[functionRunnable.getReplaceParamIndex()] = invoke;
                }
                functionRunnable.run();
            }
        }
    }
}
