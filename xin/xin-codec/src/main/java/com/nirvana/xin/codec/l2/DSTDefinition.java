package com.nirvana.xin.codec.l2;

import com.nirvana.xin.codec.ProtocolException;

import java.util.*;

/**
 * 二级协议采用纯文本协议。Line Delimiter Base Second Level Text Protocol.简写做[DST]。
 * <p>
 * 1，以换行符为分隔符分隔元素，合法分隔符为：'\n'
 * 2，元素的顺序在DST协议中是有意义的。
 * 3，每个元素可以作为单独一个值，也可以用key-value的形式。作为单独值的元素必须以'-'起始。作为key-value形式，
 * 则使用'-'分隔key和value。key值中不允许出现'-'字符。以下是合法的协议文本示例：
 * a:[-tom\n-toms-password\n]
 * b:[username-tom\rpassword-toms-password\n]
 * c:[-tom\npassword-tom123\n]
 * <p>
 * Created by Nirvana on 2017/9/3.
 * <p>
 * version 0.2.x:
 * 现在允许key中出现特殊字符，需要对key和value进行编码处理。
 * <p>
 * Edited by Nirvana on 2018/1/17.
 */
final class DSTDefinition {

    private DSTDefinition() {
    }

    /*编码指示器*/
    private static final char ENCODING_MONITOR = '~';

    /*默认的key-value分隔符*/
    static final char SEPARATOR = '-';

    /*合法的分隔符*/
    static final char DELIMITER = '\n';

    /*左括号*/
    static final char LEFT_BRACKET = '[';

    /*右括号*/
    static final char RIGHT_BRACKET = ']';


    private static final Set<Character> SPECIAL_CHARACTERS = new HashSet<>(Arrays.asList(ENCODING_MONITOR, SEPARATOR, DELIMITER, LEFT_BRACKET, RIGHT_BRACKET));

    private static final Map<Character, String> SPEC_CHAR_ENCODED_STRING_MAP = new HashMap<>();

    static {
        SPEC_CHAR_ENCODED_STRING_MAP.put(ENCODING_MONITOR, "~0");
        SPEC_CHAR_ENCODED_STRING_MAP.put(SEPARATOR, "~1");
        SPEC_CHAR_ENCODED_STRING_MAP.put(DELIMITER, "~2");
        SPEC_CHAR_ENCODED_STRING_MAP.put(LEFT_BRACKET, "~3");
        SPEC_CHAR_ENCODED_STRING_MAP.put(RIGHT_BRACKET, "~4");
    }

    private static final Map<String, Character> ENCODED_STRING_SPEC_CHAR_MAP = new HashMap<>();

    static {
        ENCODED_STRING_SPEC_CHAR_MAP.put("~0", ENCODING_MONITOR);
        ENCODED_STRING_SPEC_CHAR_MAP.put("~1", SEPARATOR);
        ENCODED_STRING_SPEC_CHAR_MAP.put("~2", DELIMITER);
        ENCODED_STRING_SPEC_CHAR_MAP.put("~3", LEFT_BRACKET);
        ENCODED_STRING_SPEC_CHAR_MAP.put("~4", RIGHT_BRACKET);
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
                    throw new ProtocolException("DST协议解码字符串发生错误： {" + string + "} at index:" + i);
                }
                String es = String.valueOf(c) + string.charAt(++i);
                if (ENCODED_STRING_SPEC_CHAR_MAP.containsKey(es)) {
                    newSb.append(ENCODED_STRING_SPEC_CHAR_MAP.get(es));
                } else {
                    throw new ProtocolException("DST协议解码字符串发生错误： {" + string + "} at index:" + i);
                }
            } else {
                if (SPECIAL_CHARACTERS.contains(c)) {
                    throw new ProtocolException("DST协议解码字符串发生错误： {" + string + "} at index:" + i + ", 解码时遇到特殊字符：" + c);
                }
                newSb.append(c);
            }
        }
        return newSb.toString();
    }

}
