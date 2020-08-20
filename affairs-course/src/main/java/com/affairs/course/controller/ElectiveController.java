package com.affairs.course.controller;


import com.affairs.course.entity.Elective;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.dto.AbortCourseDTO;
import com.affaris.common.dto.ElectivePageDTO;
import com.affaris.common.utils.R;
import com.affaris.common.vo.ElectiveVO;
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
     * 从Redis中获取当前学生已选课程信息用于对加入课程按钮的禁用，防止重复加入
     *
     * @return
     */
    @RequestMapping("/getSelectedCoursesFromRedis")
    public R getSelectedCoursesFromRedis(@RequestBody Integer stuId) {
        List<String> courses = courseService.getSelectedCoursesFromRedis(stuId);
        if (courses == null) {
            return R.success();
        }
        return R.success().put("courses", courses);
    }

    /**
     * 从数据库中查询当前学生的已选课程用于页面展示
     *
     * @param electivePageDTO
     * @return
     */
    @RequestMapping("/getSelectedCourseFromDataBase")
    public R getSelectedCourseFromDataBase(@RequestBody ElectivePageDTO electivePageDTO) {
        Integer stuId = electivePageDTO.getStuId();
        Long current = electivePageDTO.getCurrent();
        IPage<ElectiveVO> electiveVos = electiveService.getSelectedCourseFromDataBase(stuId, current);
        return R.success().put("electiveVos", electiveVos);
    }

    /**
     * 退选课程
     *
     * @param abortCourseDTO
     * @return
     */
    @RequestMapping("/abortCourse")
    public R abortCourse(@RequestBody AbortCourseDTO abortCourseDTO) {
        String stuId = "stu_id";
        String couId = "cou_id";
        QueryWrapper<Elective> electiveQueryWrapper = new QueryWrapper<>();
        electiveQueryWrapper.eq(stuId, abortCourseDTO.getStuId()).eq(couId, abortCourseDTO.getCouId());
        // todo 退选课程后应更新课程表
        if (electiveService.remove(electiveQueryWrapper)) {
            return R.success();
        } else {
            return R.fail("删除失败！");
        }
    }
}
