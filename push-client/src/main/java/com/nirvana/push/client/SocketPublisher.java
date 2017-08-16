package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;

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

        UTF8StringPayloadPart payload = new UTF8StringPayloadPart("我发布了一条消息！！\n");
        BasePackage basePackage = new BasePackage(PackageType.PUBLISH, PackageLevel.NO_CONFIRM, false, false, null, payload);
        basePackage.output(outputStream);

        socket.close();
    }

}
