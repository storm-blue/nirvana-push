package com.nirvana.push.client;

import com.nirvana.push.protocol.BasePackage;
import com.nirvana.push.protocol.PackageLevel;
import com.nirvana.push.protocol.PackageType;
import com.nirvana.push.protocol.UTF8StringPayloadPart;

import java.io.IOException;
import java.io.InputStream;
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
        InputStream inputStream = socket.getInputStream();
        for (int i = 0; i < 10; i++) {
            UTF8StringPayloadPart payload = new UTF8StringPayloadPart("我是一只大老虎！");
            BasePackage basePackage = new BasePackage(PackageType.SUBSCRIBE, PackageLevel.NO_CONFIRM, false, false, null, payload);
            basePackage.output(outputStream);
            outputStream.write("asd撒发射点法大师傅".getBytes());
        }
        inputStream.read();
        outputStream.close();
        socket.close();
    }

}
