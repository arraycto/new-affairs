package com.affairs.course.mapper;

import com.affairs.course.entity.Elective;
import com.affaris.common.vo.ElectiveVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 选课信息 Mapper 接口
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
public interface ElectiveMapper extends BaseMapper<Elective> {
    /**
     * 从数据库中查询当前学生的已选课程用于页面展示(关联查询)
     *
     * @param page
     * @param stuId
     * @return
     */
    IPage<ElectiveVo> getSelectedCourseFromDataBase(Page<ElectiveVo> page, Integer stuId);
}
