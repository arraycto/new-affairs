package com.affairs.course.controller;


import com.affairs.course.entity.Course;
import com.affairs.course.entity.Elective;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 保存选课信息
     *
     * @param electiveTo
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody ElectiveTo electiveTo) {
        // 添加之前先看一下课程是否已达到最大人数上限
        // 一个课程只能添加一次
        // todo 事务
        // 构造查询条件
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        String couId = "cou_Id";
        String couCount = "cou_count";
        courseQueryWrapper.select(couId, couCount).eq(couId, electiveTo.getCouId());
        Course course = courseService.getOne(courseQueryWrapper);
        Integer courseCouCount = course.getCouCount();
        if (courseCouCount <= 0) {
            return R.failed("人数已达上限");
        }
        Elective elective = new Elective();
        BeanUtils.copyProperties(electiveTo, elective);
        if (electiveService.save(elective)) {
            // 最大人数减1
            courseCouCount--;
            course.setCouCount(courseCouCount);
            if (courseService.update(course, new UpdateWrapper<Course>()
                    .set(couCount, course.getCouCount())
                    .eq(couId, course.getCouId()))) {
                return R.success();
            }
        }
        return R.failed("加入失败");
    }
}
