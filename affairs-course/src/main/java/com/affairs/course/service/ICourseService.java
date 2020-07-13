package com.affairs.course.service;

import com.affairs.course.entity.Course;
import com.affaris.common.vo.CourseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

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
    IPage<Course> getCoursesPageByTeaId(Page<Course> page, Integer teaId);


    /**
     * 从Redis中获取截止当前时刻可选的课程（用于页面展示且带有抢课随机码）
     *
     * @param currentPage
     * @param size
     * @param now
     * @return
     */
    Page<CourseVo> getOptionalCoursesPageFromRedis(Long currentPage, long size, LocalDateTime now);

    /**
     * 从Redis中获取当前学生已选课程信息用于对加入课程按钮的禁用，防止重复加入
     *
     * @param stuId
     * @return
     */
    List<String> getSelectedCoursesFromRedis(Integer stuId);
}
