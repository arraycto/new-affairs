package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.mapper.CourseMapper;
import com.affairs.course.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
