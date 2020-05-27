package org.example.common.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * CompletableFuture详解<br>
 * https://colobu.com/2016/02/29/Java-CompletableFuture/
 */
public class FutureTest {

    @Test
    public void test() throws Exception {
        ExecutorService cachePool = Executors.newCachedThreadPool();
        Future<String> future = cachePool.submit(() -> {
            Thread.sleep(3000);
            return "异步任务计算结果!";
        });

        // 提交完异步任务后, 主线程可以继续干一些其他的事情.
        doSomeThingElse();

        // 为了获取异步计算结果, 可以通过 future.get和轮询机制来获取.
        String result;

        // Get方式会导致当前线程阻塞, 这显然违背了异步计算的初衷.
        // result = future.get();

        // 轮询方式虽然不会导致当前线程阻塞, 但是会导致高额的 CPU 负载.
        long start = System.currentTimeMillis();
        while (true) {
            if (future.isDone()) {
                break;
            }
        }
        System.out.println("轮询耗时:" + (System.currentTimeMillis() - start));

        result = future.get();
        System.out.println("获取到异步计算结果啦: " + result);

        cachePool.shutdown();
    }

    private void doSomeThingElse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最重要的事情干完了, 我要获取异步计算结果来执行剩下的事情.");
    }

    @Test
    public void completableFutureTest() throws Exception {

        CompletableFuture<String> completableFutureOne = new CompletableFuture<>();

        ExecutorService cachePool = Executors.newCachedThreadPool();
        cachePool.execute(() -> {
            try {
                Thread.sleep(3000);
                completableFutureOne.complete("异步任务执行结果");
                System.out.println("执行异步任务的线程是:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // WhenComplete 方法返回的 CompletableFuture 仍然是原来的 CompletableFuture 计算结果.
        CompletableFuture<String> completableFutureTwo = completableFutureOne.whenComplete((s, throwable) -> {
            System.out.println("当异步任务执行完毕时打印异步任务的执行结果: " + s);
        });

        // ThenApply 方法返回的是一个新的 completeFuture.
        CompletableFuture<Integer> completableFutureThree = completableFutureTwo.thenApply(s -> {
            System.out.println("当异步任务执行结束时, 根据上一次的异步任务结果, 继续开始一个新的异步任务!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.length();
        });

        System.out.println("阻塞方式获取执行结果:" + completableFutureThree.get());

        cachePool.shutdown();
    }

}
