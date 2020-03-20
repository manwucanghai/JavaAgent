package com.zws.agent.inject.data;

import com.zws.agent.inject.IMessage;

/**
 * @author zhengws
 * @date 2020-03-15 17:05
 */
public class RunTimeData implements IMethodData , IMessage {
    /**
     * 方法ID
     */
    private long methodId;

    /**
     * 调用序号
     */
    private int sequence;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 调用栈，层级数
     */
    private int stackDepth;

    /**
     * 耗时
     */
    private long useTime;

    /**
     * 扩展类型
     */
    private String type;

    /**
     * 循环次数
     */
    private int loopNum;


    public RunTimeData(long methodId, long startTime) {
        this.methodId = methodId;
        this.startTime = startTime;
    }

    public long getMethodId() {
        return methodId;
    }

    public void setStackDepth(int stackDepth) {
        this.stackDepth = stackDepth;
    }

    public int getStackDepth() {
        return stackDepth;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        setUseTime(endTime - startTime);
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public int loopNumIncrementAndGet() {
        loopNum += 1;
        return loopNum;
    }

    public int loopNumDecrementAndGet() {
        loopNum -= 1;
        return loopNum;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String generateMessage() {
        return "methodId: " + methodId + ", sequence: " + sequence + ", stackDepth: " + stackDepth + ", useTime: " + useTime + ", type: " + type;
    }
}
