package com.zws.agent.report;

import com.zws.agent.inject.IMessage;
import com.zws.agent.inject.MethodInject;
import com.zws.agent.inject.data.RunTimeData;

/**
 * @author zhengws
 * @date 2020-03-15 22:25
 */
public class MethodInvokeTimeReport implements IReport {

    public void sendMessage(IMessage message) {
        System.out.println(message.generateMessage());
    }

    public void run() {
        while (true){
            try {
                RunTimeData runTimeData = MethodInject.runTimeQueue.take();
                sendMessage(runTimeData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
