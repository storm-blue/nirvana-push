package com.nirvana.xin.codec.encoder;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Nirvana on 2018/1/15.
 */
public abstract class AbstractCollectionEncoder<T> implements Encoder<T> {

    public byte[] encode(Collection<T> objects) {
        byte[][] arrays = new byte[objects.size()][];
        Iterator<T> iterator = objects.iterator();
        int i = 0;
        int size = 0;
        while (iterator.hasNext()) {
            arrays[i] = encode(iterator.next());
            size += arrays[i].length;
            i++;
        }
        byte[] result = new byte[size];
        int pos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

}
