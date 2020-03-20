package com.zws.agent.report;

import com.zws.agent.inject.IMessage;
import com.zws.agent.inject.method.MethodInfo;
import com.zws.agent.inject.method.MethodInjectMapper;

/**
 * @author zhengws
 * @date 2020-03-15 11:34
 */
public class MethodInjectInforReport implements IReport {
    public void run() {
        MethodInfo method = null;
        while (true) {
            try {
                method = MethodInjectMapper.methodQueue.take();
                sendMessage(method);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(IMessage message) {
        System.out.println(message.generateMessage());
    }
}
