package org.wenruo.ohh.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wenruo.util.threadpool.ResizeableCapacityLinkedBlockingQueue;
import org.wenruo.util.threadpool.ThreadPoolMonitor;
import org.wenruo.util.threadpool.ThreadPoolUtil;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

@RequestMapping("tp")
@RestController
public class ThreadPoolController {
    private String N = "\n";

    @RequestMapping("query")
    public Object query(@RequestParam String key) {
        ThreadPoolExecutor pool = ThreadPoolUtil.get(key);
        if (pool == null) {
            return "没有key=" + key + "的线程池";
        }
        return new ThreadPoolMonitor(key,pool);

    }

    @RequestMapping("addWork")
    public Object addWork(@RequestParam String key,@RequestParam String queryClass, @RequestParam Integer count) {

        ThreadPoolExecutor pool = ThreadPoolUtil.create(key, 3, 5, new ResizeableCapacityLinkedBlockingQueue<>(10));
        for (int i = 0; i < count; i++) {
            int finalI = i;
            pool.execute(new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行第" + finalI + "个任务");
            }));
        }
        return "ok";

    }

    @RequestMapping("updatePool")
    public Object hello(@RequestParam String key,
                        @RequestParam Integer corePoolSize,
                        @RequestParam Integer maximumPoolSize,
                        @RequestParam Integer queueCapacity) {

        ThreadPoolUtil.update(key,corePoolSize,maximumPoolSize,queueCapacity);
        return "ok";

    }

}


