package com.nirvana.push.test;

import com.nirvana.push.protocol.HeaderPart;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;

/**
 * com.nirvana.push.test.MainTest.java.
 * <p>
 * Created by Nirvana on 2017/8/10.
 */
public class MainTest {

    public static void main(String[] args) {
        HeaderPart part = new HeaderPart(PackageType.CONNECT, PackageLevel.AT_LEAST_ONCE, true, true);
        HeaderPart part2 = new HeaderPart(part.getByteBuf());
        System.out.println(part2);
    }

}
