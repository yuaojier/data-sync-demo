package thread;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChildThreadException extends Exception {

    private static final long serialVersionUID = 5682825039992529875L;
    /**
     * 子线程的异常列表
     */
    private List<Exception> exceptionList;

    /**
     * 锁
     */
    private Lock lock;

    public ChildThreadException() {
        super();
        initial();
    }

    public ChildThreadException(String message) {
        super(message);
        initial();
    }

    public ChildThreadException(String message, StackTraceElement[] stackTrace) {
        this(message);
        setStackTrace(stackTrace);
    }

    private void initial() {
        exceptionList = new ArrayList<Exception>();
        lock = new ReentrantLock();
    }

    /**
     * 子线程是否有异常
     *
     * @return
     */
    public boolean hasException() {
        return exceptionList.size() > 0;
    }

    /**
     * 添加子线程的异常
     *
     * @param e
     */
    public void addException(Exception e) {
        try {
            lock.lock();
            e.setStackTrace(e.getStackTrace());
            exceptionList.add(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取子线程的异常列表
     *
     * @return
     */
    public List<Exception> getExceptionList() {
        return exceptionList;
    }

    /**
     * 清空子线程的异常列表
     */
    public void clearExceptionList() {
        if (CollectionUtil.isNotEmpty(exceptionList)) {
            exceptionList.clear();
        }
    }

    /**
     * 获取所有子线程异常的堆栈跟踪信息
     *
     * @return
     */
    public String getAllStackTraceMessage() {
        StringBuffer sb = new StringBuffer();
        for (Exception e : exceptionList) {
            sb.append(e.getClass().getName());
            sb.append(": ");
            sb.append(e.getMessage());
            sb.append("\n");
            sb.append(ExceptionUtil.stacktraceToOneLineString(e));
        }
        return sb.toString();
    }

    /**
     * 打印所有子线程的异常的堆栈跟踪信息
     */
    public void printAllStackTrace() {
        printAllStackTrace(System.err);
    }

    /**
     * 打印所有子线程的异常的堆栈跟踪信息
     *
     * @param s
     */
    public void printAllStackTrace(PrintStream s) {
        for (Exception e : exceptionList) {
            e.printStackTrace(s);
        }
    }
}
