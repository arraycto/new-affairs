package com.affairs.killers.service;

/**
 * @author Vulgarities
 */
public interface IKillersService {
    /**
     * 获取截止未来一天内可选的课程缓存到Redis中
     */
    void saveOptionalCoursesToRedis();

    /**
     * 加入课程
     *
     * @param stuId
     * @param couId
     * @param teaId
     * @param randomCode
     * @return
     */
    boolean grab(Integer stuId, Integer couId, Integer teaId, String randomCode);
}
