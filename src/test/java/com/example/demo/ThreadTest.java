package com.example.demo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName : ThreadTest
 * @Description :
 * @Author : cuiweihua
 * @Date: 2020-12-16 20:50
 */
public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        AtomicLong atomicLong = new AtomicLong();
        while (true){
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(atomicLong.incrementAndGet());
                }
            });
        }
    }
}
