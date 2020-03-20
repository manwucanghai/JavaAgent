package com.zws.agent.inject.data;

import com.zws.agent.utils.ClassUtils;
import sun.misc.Unsafe;

/**
 * 写入环形对列为，无锁原子性操作。
 *
 * @author zhengws
 * @date 2020-03-15 20:50
 */
public class RingArrayQueue<T extends IResultData> implements IRingQueue<T> {
    private static final Unsafe unsafe = ClassUtils.getUnsafe();

    private static final long inertOffset;

    static {
        try {
            inertOffset = unsafe.objectFieldOffset
                    (RingArrayQueue.class.getDeclaredField("insert"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private final IResultData[] entries;

    private final int indexMask;

    private volatile int insert;

    private int size;

    public RingArrayQueue() {
        this(12);
    }

    /**
     * 12 为 4096个数据
     *
     * @param places 位数.
     */
    public RingArrayQueue(int places) {
        this.size = 1 << places;
        this.entries = new IResultData[size];
        this.indexMask = size - 1;
        this.insert = -1;
    }

    public int next() {
        boolean success = false;
        int update;
        do {
            int expect = insert;
            update = (expect + 1) & indexMask;
            success = unsafe.compareAndSwapInt(this, inertOffset, expect, update);
        } while (!success);
        return update;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) entries[index];
    }
}
