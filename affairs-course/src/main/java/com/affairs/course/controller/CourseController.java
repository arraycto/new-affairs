package com.affairs.course.controller;


import com.affairs.course.entity.Course;
import com.affairs.course.service.ICourseService;
import com.affaris.common.to.TeacherTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程信息 前端控制器
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/course/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    /**
     * 新建课程
     *
     * @param course
     * @return
     */
    @RequestMapping("/add")
    public R add(@RequestBody Course course) {
        // 处理teaId

        if (!courseService.save(course)) {
            return R.failed("添加课程失败");
        }
        return R.success();
    }

    @RequestMapping("/list/teaId")
    public R listByTeaId(@RequestBody TeacherTo teacherTo) {
        // 构造条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cou_builder", teacherTo.getTeaId());
        List<Course> courseList = courseService.list(courseQueryWrapper);
        return R.success().put("courseList", courseList);
    }
}
