package com.zws.agent.inject.method;

/**
 * @author zhengws
 * @date 2020-03-15 11:06
 */
public interface IMethodMapper {

    /**
     * 更新行数
     * @param lineNum
     */
    void updateLineNumber(int lineNum);

    /**
     * 设置方法信息
     * @param methodName
     * @param className
     */
    void setMethodInfo(String methodName, String className);

    /**
     * 提交到队列中
     */
    void submitMethod();

    /**
     * 获取当前方法id
     * @return
     */
    long getMethodId();
}
