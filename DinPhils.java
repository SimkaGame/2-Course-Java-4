import java.util.concurrent.Semaphore;

class Phil extends Thread {
    private final int id;
    private final Semaphore waiter;
    private final Semaphore leftFork;
    private final Semaphore rightFork;

    public Phil(int id, Semaphore waiter, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.waiter = waiter;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {
        System.out.println("Философ " + id + " размышляет.");
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        System.out.println("Философ " + id + " ест.");
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            think();
            try {
                waiter.acquire();
                leftFork.acquire();
                rightFork.acquire();

                eat();

                leftFork.release();
                rightFork.release();
                waiter.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class DinPhils {
    public static void main(String[] args) {
        int numPhils = 5;
        Semaphore waiter = new Semaphore(2);
        Semaphore[] forks = new Semaphore[numPhils];
        Phil[] phils = new Phil[numPhils];

        for (int i = 0; i < numPhils; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < numPhils; i++) {
            phils[i] = new Phil(i, waiter, forks[i], forks[(i + 1) % numPhils]);
            phils[i].start();
        }
    }
}
