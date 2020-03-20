package com.zws.agent.inject.data;

import com.zws.agent.config.AgentConfig;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhengws
 * @date 2020-03-15 17:18
 */
public class LinkedFilterTree<T extends IMethodData> implements IFilterTree<T> {

    private final AtomicInteger sequence = new AtomicInteger(0);
    /**
     * 最大栈深度
     */
    private final static int maxStackDepth = AgentConfig.maxStackDepth;

    /**
     * 当前层级
     */
    private Node<T> curNode;


    public boolean add(T element) {
        element.loopNumIncrementAndGet();
        int index = sequence.incrementAndGet();
        element.setSequence(index);
        if (curNode == null) {
            curNode = new Node<T>(element, 1, null);
            return true;
        }

        int nextStack = curNode.stackDepth + 1;
        if (nextStack > maxStackDepth) {
            return false;
        }
        curNode = new Node<T>(element, nextStack, curNode);
        return true;
    }

    public boolean isMatch(long methodId) {
        if (curNode == null){
            return false;
        }
        return curNode.element.getMethodId() == methodId;
    }

    public boolean isReachMaxDepth() {
        if (curNode == null) {
            return false;
        }
        return curNode.stackDepth + 1 > maxStackDepth;
    }

    public void incrementLoopNum() {
        this.curNode.element.loopNumIncrementAndGet();
    }

    public T remove(long methodId) {
        if (!isMatch(methodId)) {
            return null;
        }
        final Node<T> node = curNode;
        T element = null;
        if (node != null) {
            element = node.element;
            int num = element.loopNumDecrementAndGet();
            if (num == 0) {
                curNode = node.parent;
                node.parent = null; //help gc
            }
        }
        return element;
    }

    public boolean isTreeRoot(long methodId) {
        if (!isMatch(methodId)) {
            return false;
        }
        return curNode.parent == null;
    }

    private static class Node<T extends IMethodData> {
        /**
         * 元素信息
         */
        public T element;

        /**
         * 层级
         */
        public int stackDepth;

        /**
         * 父节点
         */
        public Node<T> parent;

        public Node(T element, int stackDepth, Node<T> parent) {
            this.element = element;
            this.stackDepth = stackDepth;
            this.element.setStackDepth(this.stackDepth);
            this.parent = parent;
        }
    }
}
