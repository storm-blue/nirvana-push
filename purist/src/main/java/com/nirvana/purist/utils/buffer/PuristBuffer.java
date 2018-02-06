package com.nirvana.purist.utils.buffer;

import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Nirvana on 2018/1/26.
 */
public abstract class PuristBuffer {

    private byte[] buf;

    private PuristBuffer previous;

    private PuristBuffer next;

    abstract void write(byte[] bytes);

    abstract void read(byte[] bytes);

    abstract void read(FileChannel fileChannel);

    abstract void read(OutputStream outputStream);

}