package com.nirvana.ezalor.test;

import com.nirvana.ezalor.core.message.MessageLevel;
import com.nirvana.ezalor.core.message.PackageType;
import com.nirvana.ezalor.protocol.HeaderPart;

/**
 * com.nirvana.push.test.MainTest.java.
 * <p>
 * Created by Nirvana on 2017/8/10.
 */
public class MainTest {

    public static void main(String[] args) {
        HeaderPart part = new HeaderPart(PackageType.CONNECT, MessageLevel.AT_LEAST_ONCE, true, true);
        HeaderPart part2 = new HeaderPart(part.getByteBuf());
        System.out.println(part2);
    }

}
