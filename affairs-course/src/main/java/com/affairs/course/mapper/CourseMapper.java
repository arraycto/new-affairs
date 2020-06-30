package com.affairs.course.mapper;

import com.affairs.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDateTime;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-28
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 分页查询指定教师开设的课程
     *
     * @param page
     * @param couBuilder
     * @return
     */
    IPage<Course> selectPageVo(Page<?> page, Integer couBuilder);

    /**
     * 分页查询”可选“的课程
     *
     * @param coursePage 分页对象
     * @param now        当前时间
     * @return
     */
    IPage<Course> selectCoursePageByTimeAndCount(Page<Course> coursePage, LocalDateTime now);
}
