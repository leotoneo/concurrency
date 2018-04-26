package com.leotoneo.concurrency;

import com.leotoneo.concurrency.annoations.UnSafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@UnSafeThread
public class ConcurrencyTest {

    //最大请求数
    public static  final int CLIENT_COUNT = 1000;
    //线程最大并发数
    public static  final int THREAD_COUNT = 100;

    private static int count = 0;


    public static void main(String [] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_COUNT);
        for (int i = 0; i < CLIENT_COUNT; i++) {

            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    addCount();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();

            });
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        semaphore.acquire();
//                        addCount();
//                        semaphore.release();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    countDownLatch.countDown();
//
//                }
//            });
        }

        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count:"+count);
    }

    private static void addCount() {
        count++;
        //System.out.println("count:" + count);
    }

}
