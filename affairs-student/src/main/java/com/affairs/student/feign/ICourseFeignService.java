package com.affairs.student.feign;

import com.affaris.common.dto.AbortCourseDTO;
import com.affaris.common.dto.ElectivePageDTO;
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
     * 从Redis中获取当前学生已选课程信息用于对加入课程按钮的禁用，防止重复加入
     *
     * @param stuId
     * @return
     */
    @RequestMapping("/course/elective/getSelectedCoursesFromRedis")
    R getSelectedCoursesFromRedis(@RequestBody Integer stuId);

    /**
     * 从数据库中查询当前学生的已选课程用于页面展示
     *
     * @param electivePageDTO
     * @return
     */
    @RequestMapping("/course/elective/getSelectedCourseFromDataBase")
    public R getSelectedCourseFromDataBase(@RequestBody ElectivePageDTO electivePageDTO);

    /**
     * 退选课程
     *
     * @param abortCourseDTO
     * @return
     */
    @RequestMapping("/course/elective/abortCourse")
    public R abortCourse(@RequestBody AbortCourseDTO abortCourseDTO);
}
