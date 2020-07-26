package com.chubin.juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrayTest implements  Runnable {

    static CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    static ExecutorService threadPool = Executors.newFixedThreadPool(5);

    static int i = 0;

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
        System.out.println("线程" + Thread.currentThread().getName() + "合并执行");
    });


    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(5,()->{
            System.out.println(Thread.currentThread().getName()+"=================合并运行");
        });

         for ( i = 0 ; i<5 ; i++){
             threadPool.execute(()->{
                 try {
                     System.out.println(Thread.currentThread().getName()+"is waiting");
                     if(i ==3){
                         throw new Exception("线程被中断");
                     }
                     cyclicBarrier1.await();
                     System.out.println(Thread.currentThread().getName()+"is working1");
                     cyclicBarrier1.await();
                     System.out.println(Thread.currentThread().getName()+"is working2");

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             });
         }
    }

    @Override
    public void run() {
    }


    private static void CyclicBarrayTest1() {

        CyclicBarrayTest1();
        new Thread(()->{
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"等待");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第一步");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第二步");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第三步");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"等待");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第一步");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第二步");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"开始执行第三步");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private static void test1() {
           try{
           new Thread(()->{
               try {
                   cyclicBarrier1.await();
                   System.out.println("开发人员开始开发");
                   cyclicBarrier2.await();
               } catch (Exception e) {
               }
           }).start();

           new Thread(()->{
               try {
                   System.out.println("产品开始整理需求");
                   cyclicBarrier1.await();
               } catch (Exception e) {
               }
           }).start();

           new Thread(()->{
               try {
                   cyclicBarrier2.await();
                   System.out.println("测试人员开始测试");
               } catch (Exception e) {
               }
           }).start();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
