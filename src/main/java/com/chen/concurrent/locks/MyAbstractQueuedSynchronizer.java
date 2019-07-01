package com.chen.concurrent.locks;

import java.io.Serializable;

/**
 * @author CXX
 * @date 2019/7/1 11:42
 */
public abstract class MyAbstractQueuedSynchronizer extends MyAbstractOwnableSynchronizer
    implements Serializable {

    /**
     * serialVersionUID是用来验证版本一致性的
     * 在兼容性升级中，在修改类的时候，不要修改serialVersionUID
     * 除非是完全不兼容的升级，才修改
     */
    private static final long serialVersionUID = 1L;

    protected MyAbstractQueuedSynchronizer() {};

















}
