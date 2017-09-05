package com.nirvana.push.protocol.p2;

import com.nirvana.push.protocol.exception.ProtocolException;

import java.util.Objects;

/**
 * DST协议元素，描述DST协议中的基本元素。
 * DSTElement有两种形式。
 * <p>
 * ===============================简单形式================================
 * 以'-'开头的值。下面都是合法的格式：
 * [-tom]
 * [-tom-and-jerry]
 * [-]
 * ===============================复合形式================================
 * key-value形式，以kv之间用'-'分隔符隔开的。如果元素中有多个'-'号，视第一个'-'号为分隔符。
 * 下面都是合法的格式：
 * [username-rose]
 * [password-rose-love-jack]
 * [password-]
 * [1-tom]
 * [1::a-tom]
 * [1::b-jack]
 * Created by Nirvana on 2017/9/5.
 */
public class DSTElement {

    private final String key;

    private final String value;

    private final boolean plain;

    public DSTElement(String value) {
        this(null, value);
    }

    public DSTElement(String key, String value) {
        key = key == null ? "" : key;
        value = value == null ? "" : value;
        if (key.contains(String.valueOf(DSTDefinition.SEPARATOR))) {
            throw new ProtocolException("Key值中不能包含字符:'-'");
        }
        this.key = key;
        this.value = value;
        this.plain = Objects.equals(key, "");
    }

    /**
     * 是否为简单DST元素。
     */
    public boolean plain() {
        return plain;
    }

    public String getKey() {
        if (plain) {
            throw new ProtocolException("此元素无key值");
        }
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     * 获取元素原始文本。
     */
    public String content() {
        return key + DSTDefinition.SEPARATOR + value;
    }

    @Override
    public String toString() {
        return content();
    }
}
