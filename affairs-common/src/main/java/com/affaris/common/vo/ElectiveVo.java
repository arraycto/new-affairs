package com.affaris.common.vo;

import java.time.LocalDateTime;

/**
 * @author Vulgarities
 */
public class ElectiveVo {
    /**
     * 课程编号
     */
    private Integer couId;

    /**
     * 课程名称
     */
    private String couName;

    /**
     * 开课教师
     */
    private Integer teaId;

    /**
     * 选课时间
     */
    private LocalDateTime electiveTime;


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

    @Override
    public String toString() {
        return "ElectiveVo{" +
                "couId=" + couId +
                ", couName='" + couName + '\'' +
                ", teaId=" + teaId +
                ", electiveTime=" + electiveTime +
                '}';
    }
}
