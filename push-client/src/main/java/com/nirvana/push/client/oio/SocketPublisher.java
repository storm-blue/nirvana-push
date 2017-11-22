package com.nirvana.push.client.oio;

import com.nirvana.push.core.message.DefaultCardBox;
import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.message.Package;
import com.nirvana.push.core.message.PackageType;
import com.nirvana.push.protocol.PayloadPart;
import com.nirvana.push.protocol.ProtocolPackage;

import java.io.*;
import java.net.Socket;

/**
 * 简单的远程发布者。
 * Created by Nirvana on 2017/8/17.
 */
public class SocketPublisher {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 32222);
        OutputStream outputStream = socket.getOutputStream();

        String string = "我发布了一条消息！！\n";
        Package pkg = new Package(PackageType.PUBLISH, MessageLevel.NO_CONFIRM, null, false, new DefaultCardBox(string));
        ProtocolPackage basePackage = ProtocolPackage.fromPackage(pkg);
        basePackage.output(outputStream);

        socket.close();
    }

}
