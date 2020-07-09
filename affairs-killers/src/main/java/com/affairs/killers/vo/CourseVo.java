package com.affairs.killers.vo;

import java.time.LocalDateTime;

/**
 * @author Vulgarities
 */
public class CourseVo {
    /**
     * 课程编号
     */
    private Integer couId;

    /**
     * 课程名称
     */
    private String couName;

    /**
     * 创建者
     */
    private String couBuilder;

    /**
     * 最大人数
     */
    private Integer couCount;

    /**
     * 选课开始时间
     */
    private LocalDateTime couTime;

    /**
     * 随机码
     */
    private String randomCode;

    @Override
    public String toString() {
        return "CourseVo{" +
                "couId=" + couId +
                ", couName='" + couName + '\'' +
                ", couBuilder='" + couBuilder + '\'' +
                ", couCount=" + couCount +
                ", couTime=" + couTime +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public String getCouBuilder() {
        return couBuilder;
    }

    public void setCouBuilder(String couBuilder) {
        this.couBuilder = couBuilder;
    }

    public Integer getCouCount() {
        return couCount;
    }

    public void setCouCount(Integer couCount) {
        this.couCount = couCount;
    }

    public LocalDateTime getCouTime() {
        return couTime;
    }

    public void setCouTime(LocalDateTime couTime) {
        this.couTime = couTime;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}
