package com.ordoAetheris.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.LockSupport;

public class Jitter {
    private Jitter() {}

    public static void micro() {
        // 1/64 шанс на паузу до 50µs
        if ((ThreadLocalRandom.current().nextInt() & 63) != 0) return;
        LockSupport.parkNanos(ThreadLocalRandom.current().nextInt(50_000));
    }
}
