package com.chubin.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLaunchDemo {

    static CountDownLatch countDownLaunch = new CountDownLatch(1);
    static CountDownLatch countDownLaunch2 = new CountDownLatch(1);
    static CountDownLatch countDownLaunch3 = new CountDownLatch(1);

    public static void main(String[] args) {
          new Thread(() -> {
            try {
                countDownLaunch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123002);
        }).start();
        new Thread(() -> {
            countDownLaunch.countDown();
            try {
                countDownLaunch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123003);
            countDownLaunch3.countDown();
        }).start();
         new Thread(() -> {
            countDownLaunch2.countDown();
            try {
                countDownLaunch3.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123004);
        }).start();
    }
}
