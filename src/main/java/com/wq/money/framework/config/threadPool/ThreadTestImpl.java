package com.wq.money.framework.config.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadTestImpl implements ThreadTest{
    private final Logger logger = LoggerFactory.getLogger(ThreadTestImpl.class);
    @Async("asyncServiceExecutor")
    @Override
    public void test1() throws Exception{
        for (int i =0; i<10; i++){
            Thread.sleep(1000);
            logger.info(Thread.currentThread().getId()+"--"+i);
        }
    }
    @Async("asyncServiceExecutor")
    @Override
    public void test2() {
        for (int i =0; i<10; i++){
            logger.info(Thread.currentThread().getId()+"--"+i);
        }
    }
}
