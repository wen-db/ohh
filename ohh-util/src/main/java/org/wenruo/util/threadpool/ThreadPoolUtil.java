package org.wenruo.util.threadpool;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final Map<String, ThreadPoolExecutor> pools = new ConcurrentHashMap<>(16);
    private static final int keepAliveTime = 5 * 60;
    private static final TimeUnit unit = TimeUnit.SECONDS;

    public static ThreadPoolExecutor create(String key,
                                            int corePoolSize,
                                            int maximumPoolSize,
                                            BlockingQueue<Runnable> workQueue) {
        if (pools.containsKey(key)) {
            return pools.get(key);
        }
        synchronized (pools) {
            if (pools.containsKey(key)) {
                return pools.get(key);
            } else {
                ThreadFactory threadFactory = Executors.defaultThreadFactory();
                RejectedExecutionHandler handler = new AbortPolicyAndNotice();
                ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                pools.put(key, executor);
                return pools.get(key);
            }
        }

    }

    public static void update(String key,
                              int corePoolSize,
                              int maximumPoolSize,
                              int queueCapacity) {
        ThreadPoolExecutor pool = pools.get(key);
        if (pool == null) {
            return;
        }
        pool.setCorePoolSize(corePoolSize);
        pool.setMaximumPoolSize(maximumPoolSize);
        if (pool.getQueue() instanceof ResizeableCapacityLinkedBlockingQueue) {
            ((ResizeableCapacityLinkedBlockingQueue<Runnable>) pool.getQueue()).setCapacity(queueCapacity);
        } else {
            throw new RuntimeException("线程池：" + key + "使用的队列是：" + pool.getQueue().getClass() + " 请使用ResizeableCapacityLinkedBlockingQueue队列");
        }
    }

    public static ThreadPoolExecutor get(String key) {
        return pools.get(key);
    }
}
