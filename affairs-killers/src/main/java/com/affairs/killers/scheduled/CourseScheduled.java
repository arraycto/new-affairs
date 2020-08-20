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
    private IKillersService killersService;

    /**
     * 获取截止未来一天内可选的课程缓存到Redis中（定时任务）
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void saveOptionalCoursesToRedis() {
        killersService.saveOptionalCoursesToRedis();
    }
}
