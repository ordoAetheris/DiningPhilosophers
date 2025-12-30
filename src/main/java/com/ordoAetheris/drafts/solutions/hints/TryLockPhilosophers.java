package com.ordoAetheris.drafts.solutions.Impl;

import com.ordoAetheris.drafts.solutions.DiningPhilosophers;
import com.ordoAetheris.util.Jitter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockPhilosophers implements DiningPhilosophers {
    private final ReentrantLock[] forks;
    private final long tryTimeoutNanos;

    public TryLockPhilosophers(int n, Duration tryTimeout) {
        this.forks = new ReentrantLock[n];
        for (int i = 0; i < n; i++) forks[i] = new ReentrantLock();
        this.tryTimeoutNanos = tryTimeout.toNanos();
    }

    @Override
    public void eatOnce(int id) throws InterruptedException {
        int left = id;
        int right = (id + 1) % forks.length;

        while (true) {
            if (forks[left].tryLock(tryTimeoutNanos, TimeUnit.NANOSECONDS)) {
                try {
                    if (forks[right].tryLock(tryTimeoutNanos, TimeUnit.NANOSECONDS)) {
                        try {
                            return; // ate
                        } finally {
                            forks[right].unlock();
                        }
                    }
                } finally {
                    forks[left].unlock();
                }
            }
            // backoff/jitter чтобы не уйти в livelock
            Jitter.micro();
        }
    }

    @Override
    public void close() { /* no-op */ }
}
