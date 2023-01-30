package org.wenruo.util.threadpool;

import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池监控
 */
@Data
public class ThreadPoolMonitor {

    private String key;
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 活跃线程数
     */
    private int activeCount;

    /**
     * 最大线程数
     */
    private int maximumPoolSize;
    /**
     * 线程池中当前线程数
     */
    private int poolSize;
    /**
     * 活跃度百分比
     * activeCount/maximumPoolSize
     */
    private String busy;
    /**
     * 队列类型
     */
    private String queueClass;
    /**
     * 队列大小
     */
    private int queryCapacity;
    /**
     * 队列已使用大小
     */
    private int querySize;
    /**
     * 队列剩余容量
     */
    private int queryRemainingCapacity;
    /**
     * 有史以来最大线程数量
     */
    private int queryLargestPoolSize;
    /**
     * 已完成任务数量
     */
    private long completedTaskCount;


    public ThreadPoolMonitor(String key, ThreadPoolExecutor pool) {
        this.key = key;
        this.corePoolSize = pool.getCorePoolSize();
        this.maximumPoolSize = pool.getMaximumPoolSize();
        this.poolSize = pool.getPoolSize();
        BlockingQueue<Runnable> queue = pool.getQueue();
        this.queueClass = queue.getClass().getSimpleName();
        this.queryCapacity = queue.size() + queue.remainingCapacity();
        this.querySize = queue.size();
        this.queryRemainingCapacity = getQueryRemainingCapacity();
        this.activeCount = pool.getActiveCount();
        this.completedTaskCount = pool.getCompletedTaskCount();
        this.queryLargestPoolSize = pool.getLargestPoolSize();
        this.busy = (activeCount * 100 / maximumPoolSize) + "%";
    }
}
