package com.affairs.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 选课信息
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@TableName("cou_elective")
public class Elective implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选课编号
     */
    @TableId(value = "elective_id", type = IdType.AUTO)
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

    @Override
    public String toString() {
        return "Elective{" +
                "electiveId=" + electiveId +
                ", couId=" + couId +
                ", stuId=" + stuId +
                ", teaId=" + teaId +
                ", electiveTime=" + electiveTime +
                "}";
    }
}
