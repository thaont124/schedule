package hnt.socket.config.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail interviewJobDetail() {
        return JobBuilder.newJob(InterviewCheckJob.class)
                .withIdentity("interviewCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger interviewJobTrigger(JobDetail interviewJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(interviewJobDetail)
                .withIdentity("interviewTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 46))
                .build();
    }
}

