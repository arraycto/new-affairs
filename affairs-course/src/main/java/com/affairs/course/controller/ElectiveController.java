package com.affairs.course.controller;


import com.affairs.course.entity.Elective;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.to.DropTo;
import com.affaris.common.to.ElectivePageTo;
import com.affaris.common.utils.R;
import com.affaris.common.vo.ElectiveVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * 获取当前学生已选课程
     *
     * @param electivePageTo
     * @return
     */
    @RequestMapping("/getSelectedCourse")
    public R getSelectedCourse(@RequestBody ElectivePageTo electivePageTo) {
        Integer stuId = electivePageTo.getStuId();
        Long current = electivePageTo.getCurrent();
        IPage<ElectiveVo> electiveVos = electiveService.getElectiveVos(stuId, current);
        System.out.println("electiveVos = " + electiveVos);
        return R.success().put("electiveVos", electiveVos);
    }

    /**
     * 退选课程
     *
     * @param dropTo
     * @return
     */
    @RequestMapping("/drop")
    public R drop(@RequestBody DropTo dropTo) {
        String stuId = "stu_id";
        String couId = "cou_id";
        QueryWrapper<Elective> electiveQueryWrapper = new QueryWrapper<>();
        electiveQueryWrapper.eq(stuId, dropTo.getStuId()).eq(couId, dropTo.getCouId());
        if (electiveService.remove(electiveQueryWrapper)) {
            return R.success();
        } else {
            return R.failed("删除失败！");
        }
    }
}
