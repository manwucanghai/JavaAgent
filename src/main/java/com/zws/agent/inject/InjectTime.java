package com.zws.agent.inject;

/**
 * @author zhengws
 * @date 2020-03-15 14:13
 */
public class InjectTime {
    private long timestamp;
    private int loopNum;

    public InjectTime(long timestamp) {
        this.timestamp = timestamp;
        this.loopNum = 1;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void loopNumIncrement() {
        this.loopNum += 1;
    }

    public void loopNumDecrement() {
        this.loopNum -= 1;
    }

}
