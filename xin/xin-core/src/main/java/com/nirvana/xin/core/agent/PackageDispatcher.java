package com.nirvana.xin.core.agent;

import com.nirvana.xin.core.message.Package;

/**
 * Created by Nirvana on 2017/11/22.
 * The so awesome name 'PackageDispatcher' is come from 钟诚(https://github.com/zhongchengxcr).
 */
public interface PackageDispatcher {

    void dispatch(Package pkg);

    void close();

}