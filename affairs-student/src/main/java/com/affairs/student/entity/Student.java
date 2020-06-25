package com.affairs.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 学生信息
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@TableName("stu_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    private Integer stuId;

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 性别
     */
    private String stuSex;

    /**
     * 学生登录密码
     */
    private String stuPassword;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName=" + stuName +
                ", stuSex=" + stuSex +
                ", stuPassword=" + stuPassword +
                "}";
    }
}
