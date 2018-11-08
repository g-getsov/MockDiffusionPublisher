package com.oddschecker.mockdiffusionpublisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableAsync
@EnableScheduling
public class Config implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("diffusion-publisher-scheduler-task-pool");
        threadPoolTaskScheduler.initialize();
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
