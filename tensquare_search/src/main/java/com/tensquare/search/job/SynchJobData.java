package com.tensquare.search.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 定期做数据同步工作，把mysql中的数据同步到elasticsearch中去
 */
@Component
public class SynchJobData {
    //@Scheduled(fixedRat e = 3000)每秒3秒执行
    @Scheduled(cron = "30 51 21 * * ?")
    public void synchData() throws IOException {
        System.out.println("执行定时任务");
        Runtime.getRuntime().exec("cmd /c start D:\\Develop\\elasticsearch-logstash-5.6.8\\bin\\logstash-mysql.bat");
    }
}
