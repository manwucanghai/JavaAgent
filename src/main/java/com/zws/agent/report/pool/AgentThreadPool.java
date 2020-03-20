package com.zws.agent.report.pool;

import com.zws.agent.report.IReport;

import java.util.HashSet;
import java.util.concurrent.*;

/**
 * @author zhengws
 * @date 2020-03-15 11:47
 */
public class AgentThreadPool {
    /**
     * 需要上报的任务。
     */
    private static HashSet<IReport> reports = new HashSet<IReport>();

    public static void startDataSendThread() {
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                new AgentThreadFactory(), new AgentRejectHandler());

        for (IReport report : reports){
            service.execute(report);
        }
    }

    public static void addReport(IReport report){
        reports.add(report);
    }
}
