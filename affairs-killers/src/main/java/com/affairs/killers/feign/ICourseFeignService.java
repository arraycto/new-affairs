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
     * 查询所有可选课程
     *
     * @return
     */
    @RequestMapping("/course/course/list")
    R list();
}
