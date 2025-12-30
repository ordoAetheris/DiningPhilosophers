package com.ordoAetheris.solutions;


public interface DiningPhilosophers extends AutoCloseable {
    /**
     * Один цикл "поесть один раз" для философа id:
     * - захватить две вилки (ресурсы)
     * - выполнить минимальную "работу"
     * - отпустить вилки
     */
    void eatOnce(int philosopherId) throws InterruptedException;

    @Override
    void close();
}