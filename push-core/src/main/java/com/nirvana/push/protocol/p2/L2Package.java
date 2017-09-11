package com.nirvana.push.protocol.p2;

import com.nirvana.push.protocol.Outputable;
import com.nirvana.push.protocol.Releasable;

/**
 * 二级协议包。
 * Created by Nirvana on 2017/9/11.
 */
public interface L2Package extends Outputable, Releasable {

    /**
     * 根据Index获取L2Element。
     */
    L2Element getElement(int index);

    /**
     * 根据Key获取value。
     *
     * @return 如果值不存在，返回null
     */
    Object get(String key);

    /**
     * 根据index获取值。
     *
     * @return 如果值不存在，返回null
     */
    default Object get(int index) {
        return getElement(index).getValue();
    }

    /**
     * 元素数量。
     */
    int size();

}
