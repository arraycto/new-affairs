package com.affairs.course.service;

import com.affairs.course.entity.Elective;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
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
     * 保存选课信息
     *
     * @param electiveTo
     * @return
     */
    R saveElective(ElectiveTo electiveTo);
}
