package com.affairs.teacher.feign;

import com.affaris.common.dto.CourseDTO;
import com.affaris.common.dto.TeacherDTO;
import com.affaris.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Course远程调用接口
 *
 * @author Vulgarities
 */
@FeignClient("affairs-course")
public interface ICourseFeignService {
    /**
     * 新建课程
     *
     * @param courseDTO
     * @return
     */
    @RequestMapping("/course/course/addCourse")
    public R addCourse(@RequestBody CourseDTO courseDTO);

    /**
     * 获取当前教师所开设的课程
     *
     * @param teacherDTO
     * @return
     */
    @RequestMapping("/course/course/getCoursesPageByTeaId")
    public R getCoursesPageByTeaId(@RequestBody TeacherDTO teacherDTO);
}
