package com.nirvana.ezalor.client.oio;

import com.nirvana.ezalor.core.message.DefaultCardBox;
import com.nirvana.ezalor.core.message.MessageLevel;
import com.nirvana.ezalor.core.message.Package;
import com.nirvana.ezalor.core.message.PackageType;
import com.nirvana.ezalor.protocol.ProtocolPackage;

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

        String string = "!";
        Package pkg = new Package(PackageType.SUBSCRIBE, MessageLevel.NO_CONFIRM, null, false, new DefaultCardBox(string));
        ProtocolPackage basePackage = ProtocolPackage.fromPackage(pkg);
        basePackage.output(outputStream);

        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String msg = reader.readLine();
            System.out.println(msg);
        }
    }

}
