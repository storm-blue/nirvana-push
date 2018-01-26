package com.nirvana.xin.codec.encoder;

import com.nirvana.xin.codec.decoder.PackageDecoder;
import com.nirvana.purist.core.message.Package;
import com.nirvana.purist.core.message.PackageType;
import com.nirvana.purist.core.message.SimpleCard;
import org.junit.Test;

/**
 * Created by Nirvana on 2018/1/17.
 */
public class PackageEncoderTest {

    private PackageEncoder encoder = PackageEncoder.getInstance();

    private PackageDecoder decoder = PackageDecoder.getInstance();

    @Test
    public void encode() {
        Package pkg0 = new Package(PackageType.CONNECT);
        pkg0.addCard(new SimpleCard("key1-", "-value1"));
        pkg0.addCard(new SimpleCard("key2~", "~value2"));
        pkg0.addCard(new SimpleCard("key3#", "#value3"));
        byte[] bytes = encoder.encode(pkg0);
        Package pkg = decoder.decode(bytes);
        System.out.println(pkg);
    }
}