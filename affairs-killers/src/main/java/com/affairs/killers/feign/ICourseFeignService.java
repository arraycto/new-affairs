package com.affairs.killers.feign;

import com.affaris.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vulgarities
 */
@FeignClient("affairs-course")
public interface ICourseFeignService {
    /**
     * 获取截止未来一天内可选的课程（用于定时任务缓存可选课程信息）
     *
     * @return
     */
    @RequestMapping("/course/course/getOptionalCourses")
    R getOptionalCourses();
}
