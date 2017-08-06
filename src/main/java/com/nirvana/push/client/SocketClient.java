package com.nirvana.push.client;

import com.nirvana.push.protocol.MessageType;
import com.nirvana.push.protocol.Package;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        IThread thread = new IThread();

        for (int i = 0; i < 10; i++) {
            new Thread(thread).start();
        }

    }

    public static class IThread implements Runnable {

        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1", 32222);
                OutputStream outputStream = socket.getOutputStream();
                for (int i = 0; i < 10; i++) {
                    Package p = new Package(MessageType.PAYLOAD, "我是一只大老虎！我的编号是：" + i);
                    p.output(outputStream);
                }
                outputStream.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
