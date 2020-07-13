package com.affairs.course.controller;


import com.affairs.course.entity.Course;
import com.affairs.course.service.ICourseService;
import com.affaris.common.to.TeacherTo;
import com.affaris.common.utils.R;
import com.affaris.common.vo.CourseVo;
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
    @RequestMapping("/addCourse")
    public R addCourse(@RequestBody Course course) {
        if (!courseService.save(course)) {
            return R.fail("添加课程失败");
        }
        return R.success();
    }

    /**
     * 分页查询指定教师开设的课程
     *
     * @param teacherTo
     * @return
     */
    @RequestMapping("/getCoursesPageByTeaId")
    public R getCoursesPageByTeaId(@RequestBody TeacherTo teacherTo) {
        if (teacherTo == null) {
            return R.fail("请求出错");
        }
        IPage<Course> courseIPage = courseService.getCoursesPageByTeaId(new Page<Course>(teacherTo.getCurrent(), teacherTo.getSize())
                , teacherTo.getTeaId());
        return R.success().put("courseIPage", courseIPage);
    }

    /**
     * 删除课程
     *
     * @param course
     * @return
     */
    @RequestMapping("/deleteCourseByCouId")
    public R deleteCourseByCouId(@RequestBody Course course) {
        // 构造条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cou_id", course.getCouId());
        if (courseService.remove(courseQueryWrapper)) {
            return R.success();
        }
        return R.fail("删除失败");
    }

    /**
     * 编辑课程
     *
     * @param course
     * @return
     */
    @RequestMapping("/editCourse")
    public R editCourse(@RequestBody Course course) {
        // 构造条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cou_id", course.getCouId());
        // 更新数据
        if (courseService.update(course, courseQueryWrapper)) {
            return R.success();
        }
        return R.fail("修改失败");
    }

    /**
     * 从Redis中获取截止当前时刻可选的课程（用于页面展示且带有抢课随机码）
     *
     * @param currentPage
     * @return
     */
    @RequestMapping("/getOptionalCoursesPageFromRedis")
    public R getOptionalCoursesPageFromRedis(@RequestParam("currentPage") Long currentPage) {
        // 指定分页大小
        long pageSize = 12;
        Page<CourseVo> courseIPage = courseService.getOptionalCoursesPageFromRedis(currentPage, pageSize, LocalDateTime.now());
        return R.success().put("courseVoList", courseIPage);
    }

    /**
     * 获取截止未来一天内可选的课程（用于定时任务缓存可选课程信息）
     *
     * @return
     */
    @RequestMapping("/getOptionalCourses")
    public R getOptionalCourses() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        // 获取到的是未来一天将开始的课程和已开始的课程
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        courseQueryWrapper.le("cou_time", localDateTime).gt("cou_count", 0);
        List<Course> list = courseService.list(courseQueryWrapper);
        if (list != null) {
            return R.success().put("allCourse", list);
        }
        return R.fail();
    }

}
