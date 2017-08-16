package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;

import java.io.*;
import java.net.Socket;

/**
 * 简单的远程订阅者。
 * Created by Nirvana on 2017/8/17.
 */
public class SocketSubscriber {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 32222);
        OutputStream outputStream = socket.getOutputStream();

        UTF8StringPayloadPart payload = new UTF8StringPayloadPart("!");
        BasePackage basePackage = new BasePackage(PackageType.SUBSCRIBE, PackageLevel.NO_CONFIRM, false, false, null, payload);
        basePackage.output(outputStream);

        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String msg = reader.readLine();
            System.out.println(msg);
        }
    }

}
