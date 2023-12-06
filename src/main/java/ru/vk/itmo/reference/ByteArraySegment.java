package ru.vk.itmo.reference;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;

/**
 * Growable buffer with {@link ByteBuffer} and {@link MemorySegment} interface.
 *
 * @author incubos
 */
final class ByteArraySegment {
    private byte[] array;
    private MemorySegment segment;

    ByteArraySegment(final int capacity) {
        this.array = new byte[capacity];
        this.segment = MemorySegment.ofArray(array);
    }

    byte[] array() {
        return array;
    }

    MemorySegment segment() {
        return segment;
    }

    void ensureCapacity(final long size) {
        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too big!");
        }

        final int capacity = (int) size;
        if (array.length >= capacity) {
            return;
        }

        // Grow to the nearest bigger power of 2
        final int newSize = Integer.highestOneBit(capacity) << 1;
        array = new byte[newSize];
        segment = MemorySegment.ofArray(array);
    }
}
