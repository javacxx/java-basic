package com.chen.concurrent.aqs;

import sun.misc.Unsafe;

import java.io.Serializable;

/**
 * @author CXX
 * @date 2019/7/1 11:42
 */
public abstract class MyAbstractQueuedSynchronizer extends MyAbstractOwnableSynchronizer
    implements Serializable {
    private static final long serialVersionUID = 1L;

    protected MyAbstractQueuedSynchronizer() {};

    private volatile int state;

    protected final int getState() {
        return state;
    }

    protected final void setState(int state) {
        this.state = state;
    }

    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    private transient volatile Node head;
    private transient volatile Node tail;

    /** 阈值，用来判断是否需要阻塞线程 */
    static final long spinForTimeoutThreshold = 1000L;

    private Node enq(Node node) { // addToQueue
        for(;;) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHead(Node.Create())) {
                    head = tail;
                }
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    // return node predecessor
                    return t;
                }
            }
        }
    }

    private Node addWaiter(Node mode) { // mode SHARED/EXCLUSIVE
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    private void setHead(Node node) { // acquire methods only
        head = node;
        node.thread = null;
        node.prev = null;
    }






    static final class Node {
        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;

        static final int CANCELLED = 1;

        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;
        /**
         * 0,CANCELLED,SIGNAL,CONDITION,PROPAGATE
         */
        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;
        volatile Thread thread;

        Node nextWaiter;

        Node() {};

        Node (Thread thread, Node nextWaiter) { // Used by addWaiter
            thread = thread;
            nextWaiter = nextWaiter;
        }

        Node (Thread thread, int waitStatus) { // Used by Condition
            thread = thread;
            waitStatus = waitStatus;
        }

        private static final Node Create() {
            return new Node();
        }

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node getPrevNode() {
            if (prev == null) {
                throw new NullPointerException("previous node is null");
            }
            return prev;
        }
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (MyAbstractQueuedSynchronizer.Node.class.getDeclaredField("next"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private final boolean compareAndSetHead(MyAbstractQueuedSynchronizer.Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(MyAbstractQueuedSynchronizer.Node expect,
                                            MyAbstractQueuedSynchronizer.Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
}
