package com.affairs.teacher.feign;

import com.affaris.common.to.CourseTo;
import com.affaris.common.to.TeacherTo;
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
     * @param courseTo
     * @return
     */
    @RequestMapping("/course/course/add")
    public R add(@RequestBody CourseTo courseTo);

    @RequestMapping("/course/course/list/teaId")
    public R listByTeaId(@RequestBody TeacherTo teacherTo);
}
