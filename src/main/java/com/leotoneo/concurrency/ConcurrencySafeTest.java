package com.leotoneo.concurrency;

import com.leotoneo.concurrency.annoations.SafeThread;
import com.leotoneo.concurrency.annoations.UnSafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@SafeThread
public class ConcurrencySafeTest {

    //最大请求数
    public static  final int CLIENT_COUNT = 5000;
    //线程最大并发数
    public static  final int THREAD_COUNT = 100;

    private static AtomicInteger count = new AtomicInteger(0);
    private static LongAdder count1 = new LongAdder();


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
        System.out.println("count:"+count.get());
        System.out.println("count1:" + count1);
    }

    private static void addCount() {
        count.incrementAndGet();
        count1.increment();
        //count.getAndIncrement();
        //System.out.println("count:" + count);
    }

}
