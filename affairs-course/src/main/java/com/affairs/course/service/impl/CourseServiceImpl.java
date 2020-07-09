package com.affairs.course.service.impl;

import com.affairs.course.entity.Course;
import com.affairs.course.mapper.CourseMapper;
import com.affairs.course.service.ICourseService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public IPage<Course> selectCoursePage(Page<Course> page, Integer teaId) {
        return baseMapper.selectPageVo(page, teaId);
    }

    @Override
    public IPage<Course> selectCoursePageByTimeAndCount(Page<Course> coursePage, LocalDateTime now, Long current) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        // 查缓存
        String courseIPageStr = ops.get("courseIPageByTimeAndCount:" + current);
        if (StringUtils.isEmpty(courseIPageStr)) {
            // 缓存中不存在则查数据库取得数据并将其加入缓存后返回
            IPage<Course> courseIPageByTimeAndCount = baseMapper.selectCoursePageByTimeAndCount(coursePage, now);
            String jsonString = JSON.toJSONString(courseIPageByTimeAndCount);
            // 放入缓存并设置过期时间
            ops.set("courseIPageByTimeAndCount:" + current, jsonString, Duration.ofMinutes(30));
            return courseIPageByTimeAndCount;
        }
        // 缓存中存在则直接返回
        return JSON.parseObject(courseIPageStr, Page.class);
    }
}
