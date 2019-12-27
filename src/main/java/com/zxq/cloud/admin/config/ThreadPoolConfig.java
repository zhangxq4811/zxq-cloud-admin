package com.zxq.cloud.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zxq
 * @date 2019/12/27 10:21
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 默认线程池线程池
     *
     * @return taskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(64);
        //指定最大线程数
        executor.setMaxPoolSize(256);
        //队列中最大的数目
        executor.setQueueCapacity(1024);
        //线程名称前缀
        executor.setThreadNamePrefix("zxq_cloud_admin_");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(30);
        return executor;
    }

}
