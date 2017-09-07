package com.nirvana.push.core;

/**
 * 销毁接口。
 * Created by Nirvana on 2017/9/7.
 */
public interface Destroyable {

    enum DestroyStatus {

        /*未销毁*/
        NOT_DESTROY,

        /*销毁中*/
        DESTROYING,

        /*已销毁*/
        DESTROYED
    }

    /**
     * 销毁。
     *
     * @throws DestroyFailedException 假如销毁出现错误，返回此异常
     */
    void destroy() throws DestroyFailedException;

    /**
     * 获得销毁状态
     */
    DestroyStatus destroyStatus();

}
