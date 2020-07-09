package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.entity.Elective;
import com.affairs.course.mapper.ElectiveMapper;
import com.affairs.course.service.ICourseService;
import com.affairs.course.service.IElectiveService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 选课信息 服务实现类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@Service
public class ElectiveServiceImpl extends ServiceImpl<ElectiveMapper, Elective> implements IElectiveService {
    @Autowired
    private ICourseService courseService;

    @Transactional
    @Override
    public R saveElective(ElectiveTo electiveTo) {
        Elective elective = new Elective();
        BeanUtils.copyProperties(electiveTo, elective);
        if (baseMapper.insert(elective) > 0) {
            // 插入成功课程最大人数发生变化
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
            Integer couId = electiveTo.getCouId();
            courseQueryWrapper.select("cou_count").eq("cou_id", couId);
            Course course = courseService.getOne(courseQueryWrapper);
            Integer couCount = course.getCouCount() - 1;
            // 构造条件更新课程最大人数
            UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
            courseUpdateWrapper.set("cou_count", couCount).eq("cou_id", couId);
            if (courseService.update(courseUpdateWrapper)) {
                return R.success();
            } else {
                return R.failed("加入课程失败");
            }
        }
        return R.failed("加入课程失败");
    }
}
