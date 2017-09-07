package com.nirvana.push.core.agent;

import com.nirvana.push.protocol.BasePackage;

/**
 * 客户端在此服务器的代理。一个Agent负责接收客户的指令，执行指令。
 * 原则上一个Agent应当唯一的绑定一个SocketChannel.
 * Created by Nirvana on 2017/8/7.
 */
public interface Agent {

    /**
     * 接收到客户端命令时触发。
     */
    void onCommand(BasePackage pkg);

    /**
     * 像远程发送一个协议包。
     */
    void sendPackage(BasePackage pkg);

    /**
     * 断开连接。
     */
    void disconnect();

}
