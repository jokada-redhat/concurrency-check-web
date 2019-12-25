package org.example;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class PeekCounter {

    public static final AtomicLong PEEK_HOLDER = new AtomicLong(-1);

    public static final LongAdder COUNTER = new LongAdder();

    public static void increment() {
        COUNTER.increment();
        synchronized (PeekCounter.class) {
            PEEK_HOLDER.set(Math.max(PEEK_HOLDER.get(), COUNTER.longValue()));
        }
    }

    public static void decrement() {
        COUNTER.decrement();
    }

    public static long current() {
        return COUNTER.longValue();
    }

    public static long peek() {
        return PEEK_HOLDER.get();
    }

    public static void reset() {
        COUNTER.reset();
        PEEK_HOLDER.set(-1);
    }

}