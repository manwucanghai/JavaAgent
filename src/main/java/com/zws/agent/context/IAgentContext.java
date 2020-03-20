package com.zws.agent.context;

/**
 * @author zhengws
 * @date 2020-03-14 21:54
 */
public interface IAgentContext {

    /**
     * 判断是否需要过滤匹配
     * @param className
     * @return
     */
    boolean isMatch(String className);

    /**
     * 设置包匹配规则，正则匹配。
     * @param pattern
     */
    void setPackagePattern(String pattern);
}
