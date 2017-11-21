package com.nirvana.push.client.oio;

import com.nirvana.push.core.message.MessageLevel;
import com.nirvana.push.core.message.PackageType;
import com.nirvana.push.protocol.ProtocolPackage;
import com.nirvana.push.protocol.UTF8StringPayloadPart;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws IOException {
        sendPackage();
    }

    public static class IThread implements Runnable {

        @Override
        public void run() {
            try {
                sendPackage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void sendPackage() throws IOException {
        Socket socket = new Socket("127.0.0.1", 32222);
        OutputStream outputStream = socket.getOutputStream();
        UTF8StringPayloadPart payload = new UTF8StringPayloadPart("我是一只大老虎！");
        ProtocolPackage basePackage = new ProtocolPackage(PackageType.PUBLISH, MessageLevel.NO_CONFIRM, false, null, payload);
        basePackage.output(outputStream);
        socket.close();
    }

}
