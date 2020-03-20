package com.zws.agent;

import com.zws.agent.context.AgentSetupContext;
import com.zws.agent.context.IAgentContext;
import com.zws.agent.report.MethodInjectInforReport;
import com.zws.agent.report.MethodInvokeTimeReport;
import com.zws.agent.report.pool.AgentThreadPool;
import com.zws.agent.transformer.ClassFileTransformerAdapter;

import java.lang.instrument.Instrumentation;

/**
 * @author zhengws
 * @date 2020-03-14 20:47
 */
public class AgentBootStrap {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("premain call");
        IAgentContext context = new AgentSetupContext();
//        context.setPackagePattern("org.springframework.boot.*");
        context.setPackagePattern("com.zws.springboot.controller.*;com.zws.springboot.service.*");
        ClassFileTransformerAdapter transformerAdapter = new ClassFileTransformerAdapter(context);
        instrumentation.addTransformer(transformerAdapter);

        startReport();
    }

    private static void startReport() {
        AgentThreadPool.addReport(new MethodInjectInforReport());
        AgentThreadPool.addReport(new MethodInvokeTimeReport());
        AgentThreadPool.startDataSendThread();
    }

}
