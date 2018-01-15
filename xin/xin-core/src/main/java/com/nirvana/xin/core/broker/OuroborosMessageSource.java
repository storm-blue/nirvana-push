package com.nirvana.xin.core.broker;

import com.nirvana.xin.utils.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Nirvana on 2017/11/20.
 */
public class OuroborosMessageSource implements MarkableMessageSource {

    private Ouroboros ouroboros = new Ouroboros(40, 10000);

    @Override
    public void putMessage(Object message) {
        ouroboros.add(message);
    }

    @Override
    public void putMessage(Collection<Object> messages) {
        for (Object object : messages) {
            ouroboros.add(object);
        }
    }

    public Pair<Long, Object> consumer(long mark) {
        return ouroboros.get(mark + 1);
    }

    public Pair<Long, Collection<Object>> consumer(long mark, int maxNum) {
        return ouroboros.get(mark + 1, maxNum);
    }

    private class Ouroboros {

        private ReadWriteLock lock = new ReentrantReadWriteLock();

        private final int unitSize;

        private final int unitCapacity;

        private Object[][] units;

        private volatile long index = 0;

        Ouroboros(int unitSize, int unitCapacity) {
            this.unitSize = unitSize;
            this.unitCapacity = unitCapacity;
            units = new Object[unitSize][];
        }

        void add(Object object) {
            lock.writeLock().lock();
            try {
                int unitIndex = (int) ((index / unitCapacity) % unitSize);
                if (units[unitIndex] == null) {
                    units[unitIndex] = new Object[unitCapacity];
                }
                int capacityIndex = (int) (index % unitCapacity);
                units[unitIndex][capacityIndex] = object;
                index++;
            } finally {
                lock.writeLock().unlock();
            }
        }

        Pair<Long, Object> get(long mark) {
            lock.readLock().lock();
            try {
                if (mark >= index) {
                    return null;
                }
                if (index - mark > unitSize * unitCapacity) {
                    mark = index - unitSize * unitCapacity;
                }
                int unitIndex = (int) ((mark / unitCapacity) % unitSize);
                int capacityIndex = (int) (mark % unitCapacity);
                Object object = units[unitIndex][capacityIndex];
                return new Pair<>(mark, object);
            } finally {
                lock.readLock().unlock();
            }

        }

        Pair<Long, Collection<Object>> get(long mark, int length) {
            lock.readLock().lock();
            try {
                if (mark > index) {
                    return null;
                }
                if (index - mark > unitSize * unitCapacity) {
                    mark = index - unitSize * unitCapacity;
                }
                long endMark = mark + length;
                if (endMark > index) {
                    endMark = index;
                }

                List<Object> objects = new ArrayList<>((int) (endMark + 1 - mark));
                for (long i = mark; i < endMark + 1; i++) {
                    int unitIndex = (int) ((i / unitCapacity) % unitSize);
                    int capacityIndex = (int) (i % unitCapacity);
                    Object object = units[unitIndex][capacityIndex];
                    objects.add(object);
                }
                return new Pair<>(endMark, objects);
            } finally {
                lock.readLock().unlock();
            }

        }

    }
}
