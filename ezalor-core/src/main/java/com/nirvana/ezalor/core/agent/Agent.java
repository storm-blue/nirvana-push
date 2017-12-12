package com.nirvana.ezalor.core.agent;

import com.nirvana.ezalor.core.Destroyable;
import com.nirvana.ezalor.core.message.Package;

/**
 * 一个Agent是推送服务器内部组件和外界远程客户端之间沟通的桥梁。
 * 对外和对内提供两方面能力：1，负责接收远程客户端的指令，执行指令。2，负责帮助内部组件向外发送消息。
 * Agent是面向单个连接。也就是说一个远程客户端在服务器内部应该对应且仅对应一个Agent。
 * Created by Nirvana on 2017/8/7.
 */
public interface Agent extends Destroyable {

    /**
     * 接收到远程客户端命令时触发。
     */
    void onPackage(Package pkg);

    /**
     * 像远程客户端发送一个协议包。
     */
    void sendPackage(Package pkg);

    /**
     * 断开连接。
     */
    void disconnect();

}
