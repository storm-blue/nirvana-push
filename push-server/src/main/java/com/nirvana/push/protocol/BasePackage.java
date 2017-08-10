package com.nirvana.push.protocol;

/**
 * com.nirvana.push.protocol.BasePackage.java.
 * <p>
 * Created by Nirvana on 2017/8/9.
 */
public class BasePackage extends OutputableArray {

    private HeaderPart header;

    private ScalableNumberPart remainLength;

    private ScalableNumberPart sequence;

    private PayloadPart payload;

}
