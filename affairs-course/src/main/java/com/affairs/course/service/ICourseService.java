package com.affairs.course.service;

import com.affairs.course.entity.Course;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 课程信息 服务类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-28
 */
public interface ICourseService extends IService<Course> {
    /**
     * 分页查询指定教师开设的课程
     *
     * @param page
     * @param teaId
     * @return
     */
    IPage<Course> selectCoursePage(Page<Course> page, Integer teaId);

    /**
     * 分页查询可选的课程，重点在“可选”
     *
     * @param coursePage
     * @param now
     * @param current
     * @return
     */
    IPage<Course> selectCoursePageByTimeAndCount(Page<Course> coursePage, LocalDateTime now, Long current);
}
