package com.chubin.juc;

import java.util.concurrent.locks.LockSupport;

public class AlibabaThreadTest {

    static Thread t1 = null;
    static Thread t2 = null;
    static Thread t3 = null;


    static int count = 1;

    public static void main(String[] args) {

        t1 = new Thread(()->{
            while(true){

                System.out.println(Thread.currentThread().getName()+":"+count++);
                LockSupport.unpark(t2);
                if(count>100){
                    break;
                }
                LockSupport.park();
            }
        });
        t2 = new Thread(()->{
            while(true){
                LockSupport.park();
                if(count>100){
                    LockSupport.unpark(t3);
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+count++);
                LockSupport.unpark(t3);
            }
        });
        t3 = new Thread(()->{
            while(true){
                LockSupport.park();
                if(count>100){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+count++);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

}
