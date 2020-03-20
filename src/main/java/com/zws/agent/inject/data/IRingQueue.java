package com.zws.agent.inject.data;

/**
 * @author zhengws
 * @date 2020-03-15 20:50
 */
public interface IRingQueue<T> {

    /**
     * 获取next 索引.
     * @return
     */
    int next();

    /**
     * 获取索引值
     * @param index
     * @return
     */
    T get(int index);
}
