package com.dan.yousuanshi.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class ThreadPoolConstructor {
    public static ExecutorService executor = null;

    private ThreadPoolConstructor(){}

    public static ExecutorService getInstance(){
        if(executor == null){
            executor = Executors.newCachedThreadPool();
        }
        return executor;
    }
}
