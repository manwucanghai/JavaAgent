package com.zws.agent.inject.method;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhengws
 * @date 2020-03-15 11:07
 */
public class MethodInjectMapper implements IMethodMapper {
    private static final int queueSize = 3000;

    public static final LinkedBlockingQueue<MethodInfo> methodQueue = new LinkedBlockingQueue<MethodInfo>(queueSize);

    private static final AtomicLong methodId = new AtomicLong(0);

    private MethodInfo methodInfo;

    public MethodInjectMapper() {
        long id = methodId.incrementAndGet();
        this.methodInfo = new MethodInfo(id);
    }

    public void updateLineNumber(int lineNum) {
        if (methodInfo != null) {
            methodInfo.setLineNum(lineNum);
        }
    }

    public void setMethodInfo(String methodName, String className) {
        methodInfo.setClassName(className);
        methodInfo.setMethodName(methodName);
    }

    public void submitMethod() {
        boolean success = methodQueue.add(this.methodInfo);
        if (!success) {
            System.out.println("方法注入，添加队列失败" + this.methodInfo.generateMessage());
        }
        /**
         * 将methodInfo置为空，避免后续更新lineNum，覆盖掉值。
         */
        this.methodInfo = null;
    }

    public long getMethodId() {
        return methodId.get();
    }
}
