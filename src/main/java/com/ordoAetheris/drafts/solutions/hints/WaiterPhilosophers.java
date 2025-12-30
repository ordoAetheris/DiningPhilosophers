package com.ordoAetheris.drafts.solutions.Impl;

import com.ordoAetheris.drafts.solutions.DiningPhilosophers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class WaiterPhilosophers implements DiningPhilosophers {
    private final ReentrantLock[] forks;
    private final Semaphore waiter;

    public WaiterPhilosophers(int n) {
        this.forks = new ReentrantLock[n];
        for (int i = 0; i < n; i++) forks[i] = new ReentrantLock();

        // максимум N-1 философов одновременно пытаются взять вилки => deadlock невозможен
        this.waiter = new Semaphore(n - 1, true); // fair=true чтобы меньше starvation
    }

    @Override
    public void eatOnce(int id) throws InterruptedException {
        int left = id;
        int right = (id + 1) % forks.length;

        waiter.acquire();
        try {
            forks[left].lock();
            try {
                forks[right].lock();
                try {
                    // eat
                } finally {
                    forks[right].unlock();
                }
            } finally {
                forks[left].unlock();
            }
        } finally {
            waiter.release();
        }
    }

    @Override
    public void close() { /* no-op */ }
}
