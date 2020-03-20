package com.zws.agent.inject;

import com.zws.agent.config.AgentConfig;
import com.zws.agent.inject.data.IFilterTree;
import com.zws.agent.inject.data.LinkedFilterTree;
import com.zws.agent.inject.data.RunTimeData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MethodInject {
    private final static ConcurrentHashMap<Long, IFilterTree<RunTimeData>> threadMap = new ConcurrentHashMap<Long, IFilterTree<RunTimeData>>();

    public static final LinkedBlockingQueue<RunTimeData> runTimeQueue = new LinkedBlockingQueue<RunTimeData>(1 << 12);

    public static void start(long methodId) {
        start(methodId, null);
    }

    public static void end(long methodId) {
        end(methodId, null);
    }

    public static void start(long methodId, String type, Object... objs) {
        long id = Thread.currentThread().getId();
        IFilterTree<RunTimeData> filterTree = threadMap.get(id);
        if (filterTree == null) {
            filterTree = new LinkedFilterTree<RunTimeData>();
            threadMap.put(id, filterTree);
        }
        if (!filterTree.isReachMaxDepth()) {
            /**
             * 自己递归调用自己不进行统计
             */
            if (filterTree.isMatch(methodId)) {
                filterTree.incrementLoopNum();
                return;
            }
            RunTimeData runTimeData = new RunTimeData(methodId, System.currentTimeMillis());
            filterTree.add(runTimeData);
        }
    }

    public static void end(long methodId, String type, Object... objs) {
        long id = Thread.currentThread().getId();
        IFilterTree<RunTimeData> filterTree = threadMap.get(id);
        if (filterTree == null) {
            throw new RuntimeException("线程队列异常, 找不到该线程的filterTree");
        }

        boolean isTreeRoot = filterTree.isTreeRoot(methodId);

        RunTimeData runTimeData = filterTree.remove(methodId);
        if (runTimeData == null) {
            System.out.println("丢失: " + methodId);
            return;
        }

//        System.out.println("##########" + runTimeData.generateMessage() + ", loopNum: " + runTimeData.getLoopNum());

        if (runTimeData.getLoopNum() == 0){
            runTimeData.setEndTime(System.currentTimeMillis());
            if (runTimeData.getUseTime() > AgentConfig.minOverheadTimeMs) {
                boolean success = false;
                try {
                    success = runTimeQueue.add(runTimeData);
                } catch (IllegalStateException e) {

                }
                if (!success) {
                    System.out.println("运行时间统计，添加队列失败" + runTimeData.generateMessage());
                }
            }
        }

        if (isTreeRoot) {
            threadMap.remove(id);
            System.out.println("删除 " + id);
        }
    }
}