package demos.twothreadrunsalternate;

import java.util.concurrent.atomic.AtomicInteger;

public class Print1ToN {
    public static void main(String[] args) {
        Object lock = new Object();
        AtomicInteger turn = new AtomicInteger(1);

        new Thread(new EvenThread(lock, turn), "EvenThread").start();
        new Thread(new OddThread(lock, turn), "OddThread").start();
    }
}

class EvenThread implements Runnable{

    final Object lock;
    AtomicInteger turn;
    int count = 2;

    public EvenThread(Object lock, AtomicInteger turn) {
        this.lock = lock;
        this.turn = turn;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while(turn.get() == 1){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

                System.out.println(Thread.currentThread().getName() + " : " + count);
                count += 2;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                turn.set(1);
                lock.notify();
            }
        }
    }
}

class OddThread implements Runnable{

    final Object lock;
    AtomicInteger turn;
    int count = 1;

    public OddThread(Object lock, AtomicInteger turn) {
        this.lock = lock;
        this.turn = turn;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (lock) {
                while(turn.get() == 2){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(Thread.currentThread().getName() + " : " + count);
                count += 2;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                turn.set(2);
                lock.notify();
            }
        }
    }
}
