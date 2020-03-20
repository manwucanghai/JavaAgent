package com.zws.agent.inject.data;

/**
 * @author zhengws
 * @date 2020-03-15 21:51
 */
public interface IResultData {
    /**
     * 判断是否数据已经被提取。
     * @return
     */
    boolean isChecked();

    /**
     * 更新提取标志。
     * @param flag
     */
    void updateChecked(boolean flag);
}
