package com.affairs.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 教师信息
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@TableName("tea_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教工号
     */
    private Integer teaId;

    /**
     * 姓名
     */
    private String teaName;

    /**
     * 性别
     */
    private String teaSex;

    /**
     * 教师登录密码
     */
    private String teaPassword;

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTeaSex() {
        return teaSex;
    }

    public void setTeaSex(String teaSex) {
        this.teaSex = teaSex;
    }

    public String getTeaPassword() {
        return teaPassword;
    }

    public void setTeaPassword(String teaPassword) {
        this.teaPassword = teaPassword;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teaId=" + teaId +
                ", teaName=" + teaName +
                ", teaSex=" + teaSex +
                ", teaPassword=" + teaPassword +
                "}";
    }
}
