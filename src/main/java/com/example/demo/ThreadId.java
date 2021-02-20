package com.example.demo;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Title: </p>
 * <p>Description: 测试雪花算法在多线程下是否会有重复（不会，因为idWorker.nextId()已经被synchronized修饰了沙雕哈哈哈）</p>
 * @author cwh
 * @date 2021/2/18 13:13
 */
public class ThreadId {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000;i++){
            pool.execute(()->{
                IdWorker idWorker = new IdWorker();
                long id = idWorker.nextId();
                if (list.contains(id)){
                    System.out.println(id+"已存在");
                }
                list.add(id);
                System.out.println(id);
            });
            countDownLatch.countDown();
        }
        countDownLatch.await();
        pool.shutdown();



    }


}
