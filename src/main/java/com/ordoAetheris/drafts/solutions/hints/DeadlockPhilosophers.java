package com.ordoAetheris.drafts.solutions.Impl;

import com.ordoAetheris.drafts.solutions.DiningPhilosophers;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockPhilosophers implements DiningPhilosophers {
    private final ReentrantLock[] forks;

    public DeadlockPhilosophers(int n) {
        this.forks = new ReentrantLock[n];
        for (int i = 0; i < n; i++) forks[i] = new ReentrantLock();
    }

    @Override
    public void eatOnce(int id) {
        int left = id;
        int right = (id + 1) % forks.length;

        // Классическая ловушка:
        // все берут левую → ждут правую → deadlock.
        forks[left].lock();
        try {
            forks[right].lock();
            try {
                // eat (ничего не делаем)
            } finally {
                forks[right].unlock();
            }
        } finally {
            forks[left].unlock();
        }
    }

    @Override
    public void close() { /* no-op */ }
}
