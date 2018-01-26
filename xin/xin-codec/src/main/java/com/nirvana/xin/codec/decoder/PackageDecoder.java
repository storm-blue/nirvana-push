package com.nirvana.xin.codec.decoder;

import com.nirvana.xin.codec.ProtocolException;
import com.nirvana.purist.core.message.CardBox;
import com.nirvana.purist.core.message.MessageLevel;
import com.nirvana.purist.core.message.Package;
import com.nirvana.purist.core.message.PackageType;
import com.nirvana.xin.codec.BitRuler;

/**
 * Created by Nirvana on 2018/1/17.
 */
public class PackageDecoder implements Decoder<Package> {

    private static final int MAX_LENGTH_REMAIN_PART_BYTES = 4;
    private static final int MAX_IDENTIFIER_PART_BYTES = 6;

    private static final PackageDecoder INSTANCE = new PackageDecoder();

    private PackageDecoder() {}

    public static PackageDecoder getInstance() {
        return INSTANCE;
    }

    private CardBoxDecoder cardBoxDecoder = CardBoxDecoder.getInstance();

    @Override
    public Package decode(byte[] bytes) {
        int pos = 0;

        //parse header
        checkCapacity(bytes, pos, 1);

        byte b = bytes[pos++];

        PackageType packageType = PackageType.get(BitRuler.r(b, 5, 8));
        if (packageType == null) {
            throw new ProtocolException("package type parse error.");
        }

        boolean identifiable = BitRuler.r(b, 4, 4) == 1;

        MessageLevel packageLevel = MessageLevel.get(BitRuler.r(b, 2, 3));
        if (packageLevel == null) {
            throw new ProtocolException("package level parse error.");
        }

        boolean retain = BitRuler.r(b, 1, 1) == 1;

        //parse length-remain
        long multiplier = 1;
        int lengthRemain = 0;
        int lengthRemainPartSize = 0;
        while (true) {
            checkCapacity(bytes, pos, 1);
            b = bytes[pos++];
            lengthRemain += (b & (byte) 127) * multiplier;
            lengthRemainPartSize++;
            if ((b & (byte) 128) == 0) {
                break;
            }
            multiplier *= 128;
            if (lengthRemainPartSize >= MAX_LENGTH_REMAIN_PART_BYTES) {
                throw new ProtocolException("package parse error: over max length-remain part size");
            }
        }

        //parse identifier
        Long identifier = null;
        int identifierPartSize = 0;
        if (identifiable) {
            multiplier = 1;
            identifier = 0L;
            while (true) {
                checkCapacity(bytes, pos, 1);
                b = bytes[pos++];
                lengthRemain += (b & (byte) 127) * multiplier;
                identifierPartSize++;
                if ((b & (byte) 128) == 0) {
                    break;
                }
                multiplier *= 128;
                if (identifierPartSize >= MAX_IDENTIFIER_PART_BYTES) {
                    throw new ProtocolException("package parse error: over max identifier part size");
                }
            }
        }

        //parse payload
        int payloadLength = lengthRemain - identifierPartSize;
        checkCapacity(bytes, pos, payloadLength);
        CardBox box = cardBoxDecoder.decode(bytes, pos, payloadLength);

        return new Package(packageType, packageLevel, identifier, retain, box);
    }

    private void checkCapacity(byte[] bytes, int pos, int length) {
        if (pos + length > bytes.length) {
            throw new ProtocolException("parse package error: not enough bytes at pos: " + pos + ", need: " + length + " find: " + (bytes.length - pos));
        }
    }

}
