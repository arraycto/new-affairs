package com.affairs.teacher.controller;


import com.affairs.teacher.entity.Teacher;
import com.affairs.teacher.feign.ICourseFeignService;
import com.affairs.teacher.service.ITeacherService;
import com.affaris.common.to.CourseTo;
import com.affaris.common.to.TeacherTo;
import com.affaris.common.utils.R;
import com.affaris.common.vo.TeacherVo;
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
     * 保存教师信息
     *
     * @param teacher
     * @return
     */
    @RequestMapping("/add")
    public R addTeacher(@RequestBody Teacher teacher) {
        if (teacher == null) {
            return R.failed("提交的内容经解析后为null");
        }
        // 查询账号是否重复
        String teaId = "tea_id";
        if (teacherService.getOne(new QueryWrapper<Teacher>().select(teaId).eq(teaId, teacher.getTeaId())) == null) {
            // 未重复则执行保存
            teacherService.save(teacher);
            return R.success();
        }
        return R.failed("账号已存在");
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
            return R.failed("提交的内容经解析后为null");
        }
        String teaId = "tea_id";
        String teaPassword = "tea_password";
        // 构造条件进行查询
        Teacher teacherServiceOne = teacherService.getOne(new QueryWrapper<Teacher>().select(teaId, teaPassword).eq(teaId, teacher.getTeaId()));
        if (teacherServiceOne != null) {
            if (teacher.getTeaPassword().equals(teacherServiceOne.getTeaPassword())) {
                // 存入session
                TeacherVo teacherVo = new TeacherVo();
                BeanUtils.copyProperties(teacher, teacherVo);
                session.setAttribute("teacherVo", teacherVo);
                return R.success();
            } else {
                return R.failed("密码有误");
            }
        }
        return R.failed("查无此人");
    }

    /**
     * 新建课程
     *
     * @param courseTo
     * @param session
     * @return
     */
    @RequestMapping("/addCourse")
    public R addCourse(@RequestBody CourseTo courseTo, HttpSession session) {
        TeacherVo teacherVo = (TeacherVo) session.getAttribute("teacherVo");
        if (teacherVo == null) {
            return R.failed("你的登录会话已过期，请前往首页重新登录");
        }
        courseTo.setCouBuilder(String.valueOf(teacherVo.getTeaId()));
        // 将UTC时间调整为CST时间
        courseTo.setCouTime(courseTo.getCouTime().plusHours(8));
        logger.info("CST:" + courseTo.getCouTime());
        courseFeignService.add(courseTo);
        return R.success();
    }

    /**
     * 查询教师所开设的课程
     *
     * @param session
     * @return
     */
    @RequestMapping("/list/teaId")
    public R listOfTeaId(@RequestParam(value = "current", defaultValue = "1") Long current, HttpSession session) {
        TeacherVo teacherVo = (TeacherVo) session.getAttribute("teacherVo");
        if (teacherVo == null) {
            return R.failed("你的登录会话已过期，请前往首页重新登录");
        }
        TeacherTo teacherTo = new TeacherTo();
        teacherTo.setCurrent(current);
        teacherTo.setSize(12);
        BeanUtils.copyProperties(teacherVo, teacherTo);
        return courseFeignService.listByTeaId(teacherTo);
    }
}
