package com.affairs.course.controller;


import com.affairs.course.entity.Elective;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

//    /**
//     * 保存选课信息
//     *
//     * @param electiveTo
//     * @return
//     */
//    @RequestMapping("/save")
//    public R save(@RequestBody ElectiveTo electiveTo) {
//        return electiveService.saveElective(electiveTo);
//    }

    /**
     * 查询学生已选课程
     *
     * @param electiveTo
     * @return
     */
    @RequestMapping("/isJoin")
    public R isJoin(@RequestBody ElectiveTo electiveTo) {
        Integer stuId = electiveTo.getStuId();
        List<Elective> courses = electiveService.list(new QueryWrapper<Elective>().select("stu_id", "cou_id").eq("stu_id", stuId));
        if (courses == null) {
            return R.success();
        }
        return R.success().put("courses", courses);
    }
}
