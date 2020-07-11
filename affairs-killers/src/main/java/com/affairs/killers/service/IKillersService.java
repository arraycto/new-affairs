package com.affairs.killers.service;

/**
 * @author Vulgarities
 */
public interface IKillersService {
    /**
     * 上架可选课程
     */
    void getCourse();

    /**
     * 抢课核心业务
     *
     * @param stuId
     * @param couId
     * @param teaId
     * @param randomCode
     * @return
     */
    boolean kill(Integer stuId, Integer couId, Integer teaId, String randomCode);
}
