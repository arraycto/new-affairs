package com.affaris.common.to;

import java.time.LocalDateTime;

/**
 * Elective远程调用传输类
 *
 * @author Vulgarities
 */
public class ElectiveTo {
    /**
     * 选课编号
     */
    private Integer electiveId;

    /**
     * 课程编号
     */
    private Integer couId;

    /**
     * 学号
     */
    private Integer stuId;

    /**
     * 开课教师
     */
    private Integer teaId;

    /**
     * 选课时间
     */
    private LocalDateTime electiveTime;
    /**
     * 随机码
     */
    private String randomCode;

    public Integer getElectiveId() {
        return electiveId;
    }

    public void setElectiveId(Integer electiveId) {
        this.electiveId = electiveId;
    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public LocalDateTime getElectiveTime() {
        return electiveTime;
    }

    public void setElectiveTime(LocalDateTime electiveTime) {
        this.electiveTime = electiveTime;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public String toString() {
        return "ElectiveTo{" +
                "electiveId=" + electiveId +
                ", couId=" + couId +
                ", stuId=" + stuId +
                ", teaId=" + teaId +
                ", electiveTime=" + electiveTime +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
