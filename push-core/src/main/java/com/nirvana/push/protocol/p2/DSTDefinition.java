package com.nirvana.push.protocol.p2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * DSTDefinition.java.
 * Created by Nirvana on 2017/9/5.
 */
final class DSTDefinition {

    /*默认的key-value分隔符*/
    static final char SEPARATOR = '-';

    /*合法的分隔符*/
    static final Set<String> delimiters = new HashSet<>(Arrays.asList("\n", "\r", "\r\n"));

}
