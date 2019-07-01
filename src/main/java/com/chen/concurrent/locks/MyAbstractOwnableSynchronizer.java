package com.chen.concurrent.locks;

import java.io.Serializable;

/**
 * 可独占synchronizer
 *
 * @author CXX
 * @date 2019/7/1 11:46
 */
public abstract class MyAbstractOwnableSynchronizer implements Serializable {

    /**
     * serialVersionUID是用来验证版本一致性的
     * 在兼容性升级中，在修改类的时候，不要修改serialVersionUID
     * 除非是完全不兼容的升级，才修改
     */
    private static final long serialVersionUID = 1L;

    protected MyAbstractOwnableSynchronizer(){};

    /**
     * transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，
     * 在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null
     */
    private transient Thread exclusiveOwnerThread;

    protected final void SetExclusiveOwnerTherad(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread(){
        return exclusiveOwnerThread;
    }
}
