package com.affairs.student.controller;


import com.affairs.student.entity.Student;
import com.affairs.student.feign.ICourseFeignService;
import com.affairs.student.service.IStudentService;
import com.affaris.common.dto.AbortCourseDTO;
import com.affaris.common.dto.ElectivePageDTO;
import com.affaris.common.utils.R;
import com.affaris.common.vo.StudentVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
     * 新增学生
     *
     * @param student
     * @return
     */
    @RequestMapping("/addStudent")
    public R addStudent(@RequestBody Student student) {
        if (student == null) {
            return R.fail("提交的内容经解析后为null");
        }
        // 查询账号是否重复
        String stuId = "stu_id";
        if (studentService.getOne(new QueryWrapper<Student>().select(stuId).eq(stuId, student.getStuId())) == null) {
            // 未重复则执行保存
            studentService.save(student);
            return R.success();
        }
        return R.fail("账号已存在");
    }

    /**
     * 学生登录
     *
     * @param student
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody Student student, HttpSession session) {
        if (student == null) {
            return R.fail("提交的内容经解析后为null");
        }
        String stuId = "stu_id";
        String stuPassword = "stu_password";
        Student studentServiceOne = studentService.getOne(new QueryWrapper<Student>().select(stuId, stuPassword).eq(stuId, student.getStuId()));
        if (studentServiceOne != null) {
            if (student.getStuPassword().equals(studentServiceOne.getStuPassword())) {
                // 保存到session中
                StudentVO studentVo = new StudentVO();
                BeanUtils.copyProperties(student, studentVo);
                session.setAttribute("studentVo", studentVo);
                return R.success();
            } else {
                return R.fail("密码有误");
            }
        }
        return R.fail("查无此人");
    }

    /**
     * 从Redis中获取当前学生已选课程信息用于对加入课程按钮的禁用，防止重复加入
     *
     * @return
     */
    @RequestMapping("/getSelectedCoursesFromRedis")
    public R getSelectedCoursesFromRedis(HttpSession session) {
        StudentVO studentVo = (StudentVO) session.getAttribute("studentVo");
        if (studentVo == null) {
            return R.fail("你的登录会话已过期，请前往首页登录");
        }
        return courseFeignService.getSelectedCoursesFromRedis(studentVo.getStuId());
    }

    /**
     * 从数据库中查询当前学生的已选课程用于页面展示
     *
     * @param session
     * @return
     */
    @RequestMapping("/getSelectedCourseFromDataBase")
    public R getSelectedCourseFromDataBase(@RequestParam(value = "currentPage", defaultValue = "1") Long currentPage, HttpSession session) {
        StudentVO studentVo = (StudentVO) session.getAttribute("studentVo");
        if (studentVo == null) {
            return R.fail("你的登录会话已过期，请前往首页登录");
        }
        ElectivePageDTO electivePageDTO = new ElectivePageDTO();
        electivePageDTO.setStuId(studentVo.getStuId());
        electivePageDTO.setCurrent(currentPage);
        return courseFeignService.getSelectedCourseFromDataBase(electivePageDTO);
    }

    /**
     * 退选课程
     *
     * @param couId
     * @param session
     * @return
     */
    @RequestMapping("/abortCourse")
    public R drop(@RequestParam("couId") Integer couId, HttpSession session) {
        StudentVO studentVo = (StudentVO) session.getAttribute("studentVo");
        if (studentVo == null) {
            return R.fail("你的登录会话已过期，请前往首页登录");
        }
        AbortCourseDTO abortCourseDTO = new AbortCourseDTO();
        abortCourseDTO.setStuId(studentVo.getStuId());
        abortCourseDTO.setCouId(couId);

        return courseFeignService.abortCourse(abortCourseDTO);
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/invoke")
    public R invokeSession(HttpSession session) {
        session.invalidate();
        return R.success();
    }
}
