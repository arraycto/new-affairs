package com.affairs.teacher.controller;


import com.affairs.teacher.entity.Teacher;
import com.affairs.teacher.feign.ICourseFeignService;
import com.affairs.teacher.service.ITeacherService;
import com.affaris.common.dto.CourseDTO;
import com.affaris.common.dto.TeacherDTO;
import com.affaris.common.utils.R;
import com.affaris.common.vo.TeacherVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 教师信息 前端控制器
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/teacher/teacher")
public class TeacherController {
    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private ICourseFeignService courseFeignService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 新增教师
     *
     * @param teacher
     * @return
     */
    @RequestMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher) {
        if (teacher == null) {
            return R.fail("提交的内容经解析后为null");
        }
        // 查询账号是否重复
        String teaId = "tea_id";
        if (teacherService.getOne(new QueryWrapper<Teacher>().select(teaId).eq(teaId, teacher.getTeaId())) == null) {
            // 未重复则执行保存
            teacherService.save(teacher);
            return R.success();
        }
        return R.fail("账号已存在");
    }

    /**
     * 教师登录
     *
     * @param teacher
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody Teacher teacher, HttpSession session) {
        if (teacher == null) {
            return R.fail("提交的内容经解析后为null");
        }
        String teaId = "tea_id";
        String teaPassword = "tea_password";
        // 构造条件进行查询
        Teacher teacherServiceOne = teacherService.getOne(new QueryWrapper<Teacher>().select(teaId, teaPassword).eq(teaId, teacher.getTeaId()));
        if (teacherServiceOne != null) {
            if (teacher.getTeaPassword().equals(teacherServiceOne.getTeaPassword())) {
                // 存入session
                TeacherVO teacherVo = new TeacherVO();
                BeanUtils.copyProperties(teacher, teacherVo);
                session.setAttribute("teacherVo", teacherVo);
                return R.success();
            } else {
                return R.fail("密码有误");
            }
        }
        return R.fail("查无此人");
    }

    /**
     * 新建课程
     *
     * @param courseDTO
     * @param session
     * @return
     */
    @RequestMapping("/addCourse")
    public R addCourse(@RequestBody CourseDTO courseDTO, HttpSession session) {
        TeacherVO teacherVo = (TeacherVO) session.getAttribute("teacherVo");
        if (teacherVo == null) {
            return R.fail("你的登录会话已过期，请前往首页重新登录");
        }
        courseDTO.setCouBuilder(String.valueOf(teacherVo.getTeaId()));
        // 将UTC时间调整为CST时间
        courseDTO.setCouTime(courseDTO.getCouTime().plusHours(8));
        logger.info("CST:" + courseDTO.getCouTime());
        courseFeignService.addCourse(courseDTO);
        return R.success();
    }

    /**
     * 获取当前教师所开设的课程
     *
     * @param session
     * @return
     */
    @RequestMapping("/getCoursesPageByTeaId")
    public R getCoursesPageByTeaId(@RequestParam(value = "currentPage", defaultValue = "1") Long currentPage, HttpSession session) {
        TeacherVO teacherVo = (TeacherVO) session.getAttribute("teacherVo");
        if (teacherVo == null) {
            return R.fail("你的登录会话已过期，请前往首页重新登录");
        }
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setCurrent(currentPage);
        teacherDTO.setSize(12);
        BeanUtils.copyProperties(teacherVo, teacherDTO);
        return courseFeignService.getCoursesPageByTeaId(teacherDTO);
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
