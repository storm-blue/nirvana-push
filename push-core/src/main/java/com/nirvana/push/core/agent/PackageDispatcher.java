package com.nirvana.push.core.agent;

import com.nirvana.push.core.message.Package;

/**
 * Created by Nirvana on 2017/11/22.
 */
public interface PackageDispatcher {

    void dispatch(Package pkg);

    void close();

}