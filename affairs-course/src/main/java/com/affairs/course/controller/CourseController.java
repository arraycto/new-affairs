package com.affairs.course.controller;


import com.affairs.course.entity.Course;
import com.affairs.course.service.ICourseService;
import com.affaris.common.to.TeacherTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    /**
     * 分页查询指定教师开设的课程
     *
     * @param teacherTo
     * @return
     */
    @RequestMapping("/list/teaId")
    public R listByTeaId(@RequestBody TeacherTo teacherTo) {
        if (teacherTo == null) {
            return R.failed("请求出错");
        }
        IPage<Course> courseIPage = courseService.selectCoursePage(new Page<Course>(teacherTo.getCurrent(), teacherTo.getSize())
                , teacherTo.getTeaId());
        return R.success().put("courseIPage", courseIPage);
    }

    /**
     * 根据couId删除课程
     *
     * @param course
     * @return
     */
    @RequestMapping("/del/couId")
    public R delByCouId(@RequestBody Course course) {
        // 构造条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cou_id", course.getCouId());
        if (courseService.remove(courseQueryWrapper)) {
            return R.success();
        }
        return R.failed("删除失败");
    }

    /**
     * 编辑课程
     *
     * @param course
     * @return
     */
    @RequestMapping("/edit")
    public R editCourse(@RequestBody Course course) {
        // 构造条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cou_id", course.getCouId());
        // 更新数据
        if (courseService.update(course, courseQueryWrapper)) {
            return R.success();
        }
        return R.failed("修改失败");
    }

    /**
     * 分页查询可选的课程
     * 满足两个条件：
     * 1. 选课开始
     * 2. 人数未满
     *
     * @param current 当前页码
     * @return
     */
    @RequestMapping("/list/time")
    public R listByTime(@RequestParam("current") Long current) {
        // 指定分页大小
        long size = 12;
        IPage<Course> courseIPage = courseService.selectCoursePageByTimeAndCount(new Page<Course>(current, size), LocalDateTime.now(), current);
        return R.success().put("courseIPage", courseIPage);
    }

    /**
     * 查询所有可选课程
     *
     * @return
     */
    @RequestMapping("/list")
    public R list() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        // 获取到的是未来一天将开始的课程和已开始的课程
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        courseQueryWrapper.le("cou_time", localDateTime).gt("cou_count", 0);
        List<Course> list = courseService.list(courseQueryWrapper);
        if (list != null) {
            return R.success().put("allCourse", list);
        }
        return R.failed();
    }

}
