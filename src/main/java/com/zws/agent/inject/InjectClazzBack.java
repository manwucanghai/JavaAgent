package com.zws.agent.inject;

import java.util.concurrent.ConcurrentHashMap;

public class InjectClazzBack {
    private final static ConcurrentHashMap<String, InjectTime> threadMap = new ConcurrentHashMap<String, InjectTime>();

    public static void start(long methodId) {
        String id = Thread.currentThread().getId() + "#" + methodId;
        long start = System.currentTimeMillis();
        InjectTime injectTime;
        if (!threadMap.containsKey(id)) {
            /**
             * 只统计首次调用方法的开始时间，也就是针对方法自己递归自己的，内层不进行调用。
             */
            injectTime = new InjectTime(start);
            threadMap.put(id, injectTime);
            System.out.println("ThreadId: " + id + ", ThreadName" + Thread.currentThread().getName() + ", StartTime: " + start);
        } else {
            injectTime = threadMap.get(id);
            injectTime.loopNumIncrement();
        }
    }

    public static void end(long methodId) {
        String id = Thread.currentThread().getId() + "#" + methodId;
        long end = System.currentTimeMillis();
        System.out.println("ThreadId: " + id + ", ThreadName" + Thread.currentThread().getName() + ", EndTime: " + end);

        InjectTime injectTime = threadMap.remove(id);
        /**
         * 针对递归调用，统计最外层.
         */
        if (injectTime.getLoopNum() > 1) {
            injectTime.loopNumDecrement();
            threadMap.put(id, injectTime);
            return;
        }
        System.out.println("ThreadId: " + id + ", ThreadName" + Thread.currentThread().getName() + ", Cost: " + (end - injectTime.getTimestamp()));
    }

    public static void start(long methodId, String type, Object... objs) {

    }

    public static void end(long methodId, String type, Object... objs) {

    }
}