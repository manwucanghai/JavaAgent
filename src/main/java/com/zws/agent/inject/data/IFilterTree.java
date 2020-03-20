package com.zws.agent.inject.data;

/**
 * @author zhengws
 * @date 2020-03-15 17:44
 */
public interface IFilterTree<T> {

    /**
     * 添加节点
     *
     * @param element
     * @return
     */
    boolean add(T element);

    /**
     * 是否匹配当前方法
     *
     * @param methodId
     * @return
     */
    boolean isMatch(long methodId);

    /**
     * 判断是否达到最大深度
     *
     * @return
     */
    boolean isReachMaxDepth();

    /**
     * 移除当前节点
     *
     * @return
     */
    T remove(long methodId);

    /**
     * 对当前节点循环次数值递减
     */
    void incrementLoopNum();

    /**
     * 判断是否是根节点.
     * @param methodId
     * @return
     */
    boolean isTreeRoot(long methodId);

}
