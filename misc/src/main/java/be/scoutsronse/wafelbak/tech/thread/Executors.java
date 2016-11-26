package be.scoutsronse.wafelbak.tech.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executors {

    public static ExecutorService newSingleThreadExecutor(String prefix) {
        return java.util.concurrent.Executors.newSingleThreadExecutor(new NamedThreadFactory(prefix));
    }

    public static ExecutorService newAugmentingThreadPool(int maxThreads, String prefix, long timeout, TimeUnit timeUnit) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(maxThreads, maxThreads, timeout, timeUnit, new LinkedBlockingQueue<>(), new NamedThreadFactory(prefix));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}