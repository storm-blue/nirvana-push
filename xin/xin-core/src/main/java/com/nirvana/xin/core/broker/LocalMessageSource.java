package com.nirvana.xin.core.broker;

import com.nirvana.purist.core.message.Message;
import com.nirvana.xin.XinConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;

/**
 * Created by Nirvana on 2018/1/23.
 */
public class LocalMessageSource implements MessageSource {

    private XinConfiguration configuration;

    @Override
    public Collection<Message> getAfter(long index) {
        return null;
    }

    @Override
    public Collection<Message> getBetween(long index1, long index2) {
        return null;
    }

    @Override
    public Message get(long index) {
        return null;
    }

    @Override
    public void putMessage(Message message) {

    }

    @Override
    public void putMessage(Collection<Message> messages) {

    }

    static class MessageFileSystem {

        private String baseFileDir;

        private long currentIndex;

        private static final int BASE_INDEX_FILE_SIZE = 64 * 1024;//64->128KB->256KB->512KB->1MB->2MB->4MB->8MB->16MB->32MB->64MB->128MB->256MB->512MB->1GB

        private static final int BASE_DATA_FILE_SIZE = 512 * 1024;//512KB->1MB->2MB->4MB->8MB->16MB->32MB->64MB->128MB->256MB->512MB->1GB

        private byte[] dataBuffer;

        private byte[] indexBuffer;

        private void createBlankFile(String fileName, long size) {
            try {
                RandomAccessFile file = new RandomAccessFile(baseFileDir + "/" + fileName, "rw");
                file.seek(size - 1);
                file.write((byte) 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * flush the data from memory to hard disk.
         */
        public void flush() {}

        /**
         * write data to message file system.
         */
        public void write(byte[] data, int pos, int length) {}

        /**
         * write data to message file system.
         */
        public void write(byte[] data) {
            write(data, 0, data.length);
        }

    }
}
