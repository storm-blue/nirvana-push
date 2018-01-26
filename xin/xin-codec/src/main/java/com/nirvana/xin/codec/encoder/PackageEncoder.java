package com.nirvana.xin.codec.encoder;

import com.nirvana.xin.codec.ProtocolException;
import com.nirvana.purist.core.message.MessageLevel;
import com.nirvana.purist.core.message.Package;
import com.nirvana.purist.core.message.PackageType;

/**
 * Created by Nirvana on 2018/1/16.
 */
public class PackageEncoder implements Encoder<Package> {

    private CardBoxEncoder cardBoxEncoder = CardBoxEncoder.getInstance();

    private ScalableNumberEncoder numberEncoder = ScalableNumberEncoder.getInstance();

    private static final PackageEncoder INSTANCE = new PackageEncoder();

    private PackageEncoder() {}

    public static PackageEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public byte[] encode(Package object) {

        //Header
        PackageType type = object.getType();
        boolean identifiable = object.getId() != null;
        MessageLevel level = object.getLevel();
        boolean retain = object.isRetain();
        byte b = (byte) (type.getCode() << 4 | (identifiable ? 1 : 0) << 3 | (level.getCode() << 1) | (retain ? 1 : 0));

        byte[] header = new byte[]{b};

        //Identifier
        byte[] identifier = null;

        if (identifiable) {
            Object id = object.getId();
            if ((id instanceof Long) || (id instanceof Integer)) {
                identifier = numberEncoder.encode((long) id);
            } else {
                throw new ProtocolException("Unsupported identifier type: " + id.getClass().getSimpleName());
            }
        }

        //Payload
        byte[] payload = cardBoxEncoder.encode(object);

        byte[] lengthRemain;

        if (identifier != null) {
            lengthRemain = numberEncoder.encode((long) (identifier.length + payload.length));
        } else {
            lengthRemain = numberEncoder.encode((long) payload.length);
        }

        return combine(header, lengthRemain, identifier, payload);
    }

    private byte[] combine(byte[]... arrays) {
        int length = 0;
        for (byte[] bytes : arrays) {
            int bytesLen = bytes == null ? 0 : bytes.length;
            length += bytesLen;
        }
        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] bytes : arrays) {
            if (bytes != null) {
                System.arraycopy(bytes, 0, result, pos, bytes.length);
                pos += bytes.length;
            }
        }
        return result;
    }

}
