package com.nirvana.push.protocol.l2;

import com.nirvana.push.protocol.exception.ProtocolException;

import java.util.*;

/**
 * DST协议定义。
 * Created by Nirvana on 2017/9/5.
 */
final class DSTDefinition {

    private DSTDefinition() {
    }

    /*默认的key-value分隔符*/
    static final char SEPARATOR = '-';

    /*合法的分隔符*/
    static final Set<String> DELIMITERS = new HashSet<>(Arrays.asList("\n", "\r", "\r\n"));

    static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList('-', '~', '\r', '\n'));

    private static final Map<Character, String> SPEC_CHAR_ENCODED_STRING_MAP = new HashMap<>();

    static {
        SPEC_CHAR_ENCODED_STRING_MAP.put('-', "~0");
        SPEC_CHAR_ENCODED_STRING_MAP.put('~', "~1");
        SPEC_CHAR_ENCODED_STRING_MAP.put('\r', "~2");
        SPEC_CHAR_ENCODED_STRING_MAP.put('\n', "~3");
    }

    private static final Map<String, Character> ENCODED_STRING_SPEC_CHAR_MAP = new HashMap<>();

    static {
        ENCODED_STRING_SPEC_CHAR_MAP.put("~0", '-');
        ENCODED_STRING_SPEC_CHAR_MAP.put("~1", '~');
        ENCODED_STRING_SPEC_CHAR_MAP.put("~2", '\r');
        ENCODED_STRING_SPEC_CHAR_MAP.put("~3", '\n');
    }

    static String encode(String string) {
        StringBuilder newSb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (SPEC_CHAR_ENCODED_STRING_MAP.containsKey(c)) {
                newSb.append(SPEC_CHAR_ENCODED_STRING_MAP.get(c));
            } else {
                newSb.append(c);
            }
        }
        return newSb.toString();
    }

    static String decode(String string) {
        StringBuilder newSb = new StringBuilder();
        int length = string.length();
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (c == '~') {
                if (i >= length - 1) {
                    throw new ProtocolException("DST协议解码字符串发生错误： [" + string + "] at index:" + i);
                }
                String es = String.valueOf(c) + string.charAt(++i);
                if (ENCODED_STRING_SPEC_CHAR_MAP.containsKey(es)) {
                    newSb.append(ENCODED_STRING_SPEC_CHAR_MAP.get(es));
                } else {
                    throw new ProtocolException("DST协议解码字符串发生错误： [" + string + "] at index:" + i);
                }
            } else {
                newSb.append(c);
            }
        }
        return newSb.toString();
    }

}
