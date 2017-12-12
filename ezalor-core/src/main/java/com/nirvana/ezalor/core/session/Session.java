package com.nirvana.ezalor.core.session;

import com.nirvana.ezalor.core.Destroyable;

/**
 * 连接会话信息。
 * Created by Nirvana on 2017/9/6.
 */
public interface Session extends Destroyable {

    /**
     * 创建时间。
     */
    long getCreationTime();

    /**
     * Session Id.
     */
    String getId();

    /**
     * 最后通信时间。
     */
    long getLastAccessedTime();

    /**
     * 设置属性。
     *
     * @param name  属性名称
     * @param value 属性值
     */
    void setAttribute(String name, Object value);

    /**
     * 根据名称获取属性。
     */
    Object getAttribute(String name);

    /**
     * 移除属性。
     */
    void removeAttribute(String name);

    /**
     * 使会话失效。
     */
    void invalidate();

}
