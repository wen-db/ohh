package org.wenruo.util.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class AbortPolicyAndNotice implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        log.error("任务入队失败 Task " + r.toString() + " rejected  " + e.toString());
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
    }
}
