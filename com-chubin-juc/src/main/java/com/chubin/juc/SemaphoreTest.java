package com.chubin.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    //模拟的线程数量
    private final static int threadCount = 20;

    private static ExecutorService fixPool = Executors.newFixedThreadPool(10);
    private static ExecutorService cachedPool = Executors.newCachedThreadPool();
    private static  ExecutorService singlePool = Executors.newSingleThreadExecutor();

    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {


      //  test1();
      //  test2();
        test3();

    }

    private static void test3() {

        for(int i = 0 ; i <10 ; i++){
            cachedPool.execute(()->{
                try {
                    semaphore.acquire();
                    System.out.println("线程"+Thread.currentThread().getName()+" 保存数据");
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println("线程" + Thread.currentThread().getName() + " 释放许可");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private static void test2() {
            //线程池
            ExecutorService exec = Executors.newCachedThreadPool();
            //同时允许3个线程执行
            final Semaphore semaphore = new Semaphore(5);

            //通过lambda表达式创建线程，同时添加进入线程池中
            for (int i = 0; i < threadCount; i++) {
                final int threadNum = i;
                exec.execute(() -> {
                    try {
                        semaphore.acquire(); // 获取一个许可
                        test(threadNum);
                        semaphore.release(); // 释放一个许可
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            exec.shutdown();
        }
    private static void test(int threadNum) throws Exception {
        System.out.println(threadNum);
        Thread.sleep(1000);
    }

    private static void test1() {

        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3);//创建Semaphore信号量，初始化许可大小为3
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            Runnable runnable = ()-> {
                try {
                    sp.acquire();//请求获得许可，如果有可获得的许可则继续往下执行，许可数减1。否则进入阻塞状态
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "进入，当前已有" + (3 - sp.availablePermits()) + "个并发");
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将离开");
                    sp.release();//释放许可，许可数加1
                    //下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "已离开，当前已有" + (3 - sp.availablePermits()) + "个并发");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            };
            service.execute(runnable);
        }

    }

}
