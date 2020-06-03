package com.leet;

import java.util.function.IntConsumer;

public class FizzBuzz {
    private int n;

    private int index = 1;
    //sync=-1 结束
    //sync=0 跳过
    //sync=1 同时不能被3和5整除
    //sync=3 只能被3整除
    //sync=5 只能被5整除
    //sync=15 同时被3和5整除
    private volatile int sync = 1;

    public FizzBuzz(int n) {
        this.n = n;
        if (n < 1) sync = -1;
    }

    private void next() {
        index++;
        if (index > n) {
            sync = -1;
        } else if (index % 15 == 0) {
            sync = 15;
        } else if (index % 5 == 0) {
            sync = 5;
        } else if (index % 3 == 0) {
            sync = 3;
        } else {
            sync = 1;
        }
    }

    private void setSync() {

    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (sync >= 0) {
            if (sync == 3) {
                printFizz.run();
                next();
            }
            Thread.sleep(1);
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (sync >= 0) {
            if (sync == 5) {
                printBuzz.run();
                next();
            }
            Thread.sleep(1);
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (sync >= 0) {
            if (sync == 15) {
                printFizzBuzz.run();
                next();
            }
            Thread.sleep(1);
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (sync >= 0) {
            if (sync == 1) {
                printNumber.accept(index);
                next();
            }
            Thread.sleep(1);
        }
    }
}
