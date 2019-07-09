package com.chen.concurrent.aqs;

/**
 * 可独占synchronizer
 *
 * @author CXX
 * @date 2019/7/1 11:46
 */
public abstract class MyAbstractOwnableSynchronizer{

    protected MyAbstractOwnableSynchronizer(){};

    private Thread exclusiveOwnerThread;

    protected final void SetExclusiveOwnerTherad(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread(){
        return exclusiveOwnerThread;
    }
}
