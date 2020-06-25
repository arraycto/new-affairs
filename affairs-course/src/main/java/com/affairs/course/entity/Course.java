package com.affairs.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 课程信息
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@TableName("cou_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @TableId(value = "cou_id", type = IdType.AUTO)
    private Integer couId;

    /**
     * 课程名称
     */
    private String couName;

    /**
     * 创建者
     */
    private Integer couBuilder;

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

    public Integer getCouBuilder() {
        return couBuilder;
    }

    public void setCouBuilder(Integer couBuilder) {
        this.couBuilder = couBuilder;
    }

    @Override
    public String toString() {
        return "Course{" +
                "couId=" + couId +
                ", couName=" + couName +
                ", couBuilder=" + couBuilder +
                "}";
    }
}
