package com.zws.agent.inject.method;

import com.zws.agent.inject.IMessage;

/**
 * @author zhengws
 * @date 2020-03-15 11:08
 */
public class MethodInfo implements IMessage {
    private long methodId;

    private String methodName;

    private String className;

    private volatile int lineNum;

    public MethodInfo(long methodId) {
        this.methodId = methodId;
    }

    public long getMethodId() {
        return methodId;
    }

    public void setMethodId(long methodId) {
        this.methodId = methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String generateMessage() {
        return "methodId: " + methodId + ",className: " + className + ", methodName: " + methodName + ", line: " + lineNum;
    }
}
