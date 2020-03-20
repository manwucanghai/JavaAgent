package com.zws.agent.inject.data;

/**
 * @author zhengws
 * @date 2020-03-15 17:36
 */
public interface IMethodData {
    /**
     * 获取方法id
     * @return
     */
    long getMethodId();

    /**
     * 设置栈深度
     * @param stackDepth
     */
    void setStackDepth(int stackDepth);

    /**
     * 循环次数自增
     */
    int loopNumIncrementAndGet();


    /**
     * 循环次数自减
     */
    int loopNumDecrementAndGet();

    /**
     * 设置上一层及方法id
     * @param sequence
     */
    void setSequence(int sequence);
}
