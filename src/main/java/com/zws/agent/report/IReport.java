package com.zws.agent.report;

import com.zws.agent.inject.IMessage;

/**
 * @author zhengws
 * @date 2020-03-15 11:36
 */
public interface IReport extends Runnable{
    /**
     * 发送消息数据
     * @param message
     */
    void sendMessage(IMessage message);
}
