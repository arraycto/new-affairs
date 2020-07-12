package com.affairs.student.feign;

import com.affaris.common.to.DropTo;
import com.affaris.common.to.ElectivePageTo;
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
@FeignClient("affairs-course")
public interface ICourseFeignService {
    /**
     * 保存选课信息
     *
     * @param electiveTo
     * @return
     */
    @RequestMapping("/course/elective/save")
    R save(@RequestBody ElectiveTo electiveTo);

    /**
     * 查询学生已选课程
     *
     * @param stuId
     * @return
     */
    @RequestMapping("/course/elective/isJoin")
    R isJoin(@RequestBody Integer stuId);

    /**
     * 获取当前学生的已选课程
     *
     * @param electivePageTo
     * @return
     */
    @RequestMapping("/course/elective/getSelectedCourse")
    public R getSelectedCourse(@RequestBody ElectivePageTo electivePageTo);

    /**
     * 退选课程
     *
     * @param dropTo
     * @return
     */
    @RequestMapping("/course/elective/drop")
    public R drop(@RequestBody DropTo dropTo);
}
