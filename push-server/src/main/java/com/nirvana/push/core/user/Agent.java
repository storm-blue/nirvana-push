package com.nirvana.push.core.user;

import com.nirvana.push.protocolv1.Package;

/**
 * 客户端在此服务器的代理。一个Agent负责接收客户的指令，执行指令。
 * Created by Nirvana on 2017/8/7.
 */
public interface Agent {

    void onRequest(Package pkg);

}
