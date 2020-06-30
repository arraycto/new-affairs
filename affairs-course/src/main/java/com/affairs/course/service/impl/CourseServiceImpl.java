package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.mapper.CourseMapper;
import com.affairs.course.service.ICourseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-28
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public IPage<Course> selectCoursePage(Page<Course> page, Integer teaId) {
        return baseMapper.selectPageVo(page, teaId);
    }

    @Override
    public IPage<Course> selectCoursePageByTimeAndCount(Page<Course> coursePage, LocalDateTime now) {
        return baseMapper.selectCoursePageByTimeAndCount(coursePage, now);
    }
}
