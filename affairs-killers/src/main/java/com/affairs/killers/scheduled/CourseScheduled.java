package com.affairs.killers.scheduled;

import com.affairs.killers.service.IKillersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author Vulgarities
 */
@Component
public class CourseScheduled {
    @Autowired
    IKillersService killersService;

    @Scheduled(cron = "0 14 3 * * ?")
    public void getCourse() {
        killersService.getCourse();
    }
}
