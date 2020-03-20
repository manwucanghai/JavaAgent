package com.zws.agent.report.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhengws
 * @date 2020-03-15 11:48
 */
public class AgentThreadFactory implements ThreadFactory {
    private static AtomicInteger threadNum = new AtomicInteger(0);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "Agent-" + threadNum.incrementAndGet());
        thread.setPriority(5);
        thread.setDaemon(true);
        return thread;
    }
}
