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
     * 获取当前学生已选课程信息
     *
     * @param page
     * @param stuId
     * @return
     */
    IPage<ElectiveVo> getElectiveVos(Page<ElectiveVo> page, Integer stuId);
}
