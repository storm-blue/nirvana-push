package com.nirvana.push.core;

/**
 * 可销毁类的虚类。提供销毁的基础逻辑。
 * Created by Nirvana on 2017/9/7.
 */
public abstract class AbstractDestroyable implements Destroyable {

    //销毁状态。
    protected volatile DestroyStatus destroyStatus = DestroyStatus.NOT_DESTROY;

    /**
     * 判断是否未销毁，如果状态为未销毁，则开始销毁动作。
     * 此处通过状态避免两个对象的destroy()方法需要互相调用时引发的问题。
     */
    @Override
    public void destroy() throws DestroyFailedException {
        if (destroyStatus == DestroyStatus.NOT_DESTROY) {
            destroyStatus = DestroyStatus.DESTROYING;
            doDestroy();
            destroyStatus = DestroyStatus.DESTROYED;
        }
    }

    /**
     * 真正的销毁动作。子类实现此方法来做销毁时的处理逻辑。
     */
    protected abstract void doDestroy() throws DestroyFailedException;

    @Override
    public DestroyStatus destroyStatus() {
        return destroyStatus;
    }
}
