package com.ordoAetheris.drafts.solutions.Impl;

import com.ordoAetheris.drafts.solutions.DiningPhilosophers;

import java.util.concurrent.locks.ReentrantLock;

public class OrderingPhilosophers implements DiningPhilosophers {
    private final ReentrantLock[] forks;

    public OrderingPhilosophers(int n) {
        this.forks = new ReentrantLock[n];
        for (int i = 0; i < n; i++) forks[i] = new ReentrantLock();
    }

    @Override
    public void eatOnce(int id) {
        int left = id;
        int right = (id + 1) % forks.length;

        int first = Math.min(left, right);
        int second = Math.max(left, right);

        // Ключ: глобальный порядок захвата исключает цикл ожиданий.
        forks[first].lock();
        try {
            forks[second].lock();
            try {
                // eat
            } finally {
                forks[second].unlock();
            }
        } finally {
            forks[first].unlock();
        }
    }

    @Override
    public void close() { /* no-op */ }
}
