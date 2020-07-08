package cn.forest.xxl.client.bean;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import cn.forest.xxl.job.core.biz.model.ReturnT;
import cn.forest.xxl.job.core.handler.annotation.XxlJob;
import cn.forest.xxl.job.core.log.XxlJobLogger;

@Component
public class TestJobBean {

	
	/**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
    	System.out.println("aaaaaaaaaa");
        XxlJobLogger.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }
}
