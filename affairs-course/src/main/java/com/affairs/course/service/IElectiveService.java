package com.affairs.course.service;

import com.affairs.course.entity.Elective;
import com.affaris.common.vo.ElectiveVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 选课信息 服务类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
public interface IElectiveService extends IService<Elective> {
    /**
     * 获取当前学生的已选课程信息
     *
     * @param stuId
     * @param current
     * @return
     */
    IPage<ElectiveVo> getElectiveVos(Integer stuId, Long current);
}
