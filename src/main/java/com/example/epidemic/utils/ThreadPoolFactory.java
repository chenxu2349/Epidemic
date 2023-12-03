package com.example.epidemic.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolFactory
 * @Description
 * @Author chenxu
 * @Date 2023/12/1 11:55
 **/
public class ThreadPoolFactory {

    public static ThreadPoolExecutor getThreadPool() {

        // 系统可用处理器核心数
        int coreNumber = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreNumber, coreNumber + 1, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        return threadPool;
    }
}
