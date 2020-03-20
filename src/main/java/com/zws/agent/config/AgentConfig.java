package com.zws.agent.config;

/**
 * @author zhengws
 * @date 2020-03-15 17:31
 */
public class AgentConfig {
    /**
     * 最大栈深度
     */
    public static int maxStackDepth = 50;

    /**
     * 最小开销时间，只有大于这个时间的才进行记录
     */
    public static int minOverheadTimeMs = -1;
}
