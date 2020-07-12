package com.affairs.course.controller;


import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 选课信息 前端控制器
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/course/elective")
public class ElectiveController {
    @Autowired
    private IElectiveService electiveService;

    @Autowired
    private ICourseService courseService;

    /**
     * 查询学生已选课程
     *
     * @return
     */
    @RequestMapping("/isJoin")
    public R isJoin(@RequestBody Integer stuId) {
        List<String> courses = courseService.joinCourseList(stuId);
        if (courses == null) {
            return R.success();
        }
        return R.success().put("courses", courses);
    }
}
