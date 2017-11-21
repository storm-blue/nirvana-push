package com.nirvana.push.protocol.l2;

/**
 * 二级协议元素。
 * Created by Nirvana on 2017/9/11.
 */
public interface L2Element {

    /**
     * 是否为简单元素。
     * 所谓简单元素指无key值只有value值的元素。
     */
    boolean plain();

    /**
     * 获取此Element的Key值。
     *
     * @throws L2ProtocolException 假如为简单元素，抛出此错误。
     */
    String getKey();

    /**
     * 获取此Element的value值。
     */
    Object getValue();

}
