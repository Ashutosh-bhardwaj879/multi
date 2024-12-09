package org.example.evenodd;

public class EvenOddAlternatively {

    private boolean isEven = true;
    private static final int LIMIT = 10;

    public static void main(String[] args) {
        EvenOddAlternatively printer = new EvenOddAlternatively();
        Thread oddThread = new Thread(printer::printOdd);
        Thread evenThread = new Thread(printer::printEven);
        oddThread.start();
        evenThread.start();
    }

    public synchronized void printEven() {
        for (int i = 2; i <= LIMIT; i += 2) {
            while (!isEven) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName() + " prints even " + i);
            isEven = false;
            notify();
        }
    }

    public synchronized void printOdd() {
        for (int i = 1; i <= LIMIT; i += 2) {
            while (isEven) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName() + " prints odd " + i);
            isEven = true;
            notify();
        }
    }

}
