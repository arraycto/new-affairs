package com.affairs.student.feign;

import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Course远程调用类
 *
 * @author Vulgarities
 */
@FeignClient("course")
public interface ICourseFeignService {
    /**
     * 保存选课信息
     *
     * @param electiveTo
     * @return
     */
    @RequestMapping("/course/elective/save")
    public R save(@RequestBody ElectiveTo electiveTo);

    /**
     * 查询学生已选课程
     *
     * @param electiveTo
     * @return
     */
    @RequestMapping("/course/elective/isJoin")
    public R isJoin(@RequestBody ElectiveTo electiveTo);
}
