package com.nirvana.push.protocol.p2;

import com.nirvana.push.protocol.UTF8StringPayloadPart;

/**
 * DSTPayloadPart.java.
 * Created by Nirvana on 2017/9/7.
 */
public class DSTPayloadPart extends UTF8StringPayloadPart {

    private DSTPackage pkg;

    public DSTPayloadPart(DSTPackage pkg) {
        super(pkg.getContent());
        this.pkg = pkg;
    }

    public DSTPackage getPkg() {
        return pkg;
    }

    public void setPkg(DSTPackage pkg) {
        this.pkg = pkg;
    }
}
