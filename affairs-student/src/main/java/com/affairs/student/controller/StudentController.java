package com.affairs.student.controller;


import com.affairs.student.entity.Student;
import com.affairs.student.feign.ICourseFeignService;
import com.affairs.student.service.IStudentService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生信息 前端控制器
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/student/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private ICourseFeignService courseFeignService;

    /**
     * 保存学生信息
     *
     * @param student
     * @return
     */
    @RequestMapping("/add")
    public R addStudent(@RequestBody Student student) {
        if (student == null) {
            return R.failed("提交的内容经解析后为null");
        }
        // 查询账号是否重复
        String stuId = "stu_id";
        if (studentService.getOne(new QueryWrapper<Student>().select(stuId).eq(stuId, student.getStuId())) == null) {
            // 未重复则执行保存
            studentService.save(student);
            return R.success();
        }
        return R.failed("账号已存在");
    }

    /**
     * 学生登录
     *
     * @param student
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody Student student, Model model, HttpSession session) {
        if (student == null) {
            return R.failed("提交的内容经解析后为null");
        }
        String stuId = "stu_id";
        String stuPassword = "stu_password";
        Student studentServiceOne = studentService.getOne(new QueryWrapper<Student>().select(stuId, stuPassword).eq(stuId, student.getStuId()));
        if (studentServiceOne != null) {
            if (student.getStuPassword().equals(studentServiceOne.getStuPassword())) {
                // 保存到session中
                session.setAttribute("student", student);
                return R.success();
            } else {
                return R.failed("密码有误");
            }
        }
        return R.failed("查无此人");
    }

    /**
     * 保存选课信息
     *
     * @param electiveTo
     * @param session
     * @return
     */
    @RequestMapping("/saveElective")
    public R saveElective(@RequestBody ElectiveTo electiveTo, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return R.failed("你的登录会话已过期，请前往首页登录");
        }
        electiveTo.setStuId(student.getStuId());
        electiveTo.setElectiveTime(LocalDateTime.now());
        return courseFeignService.save(electiveTo);
    }

    /**
     * 查询是否已选该课程
     *
     * @return
     */
    @RequestMapping("/isJoin")
    public R isJoin(HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return R.failed("你的登录会话已过期，请前往首页登录");
        }
        ElectiveTo electiveTo = new ElectiveTo();
        electiveTo.setStuId(student.getStuId());
        return courseFeignService.isJoin(electiveTo);
    }
}
