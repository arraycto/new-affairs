package com.affairs.teacher.controller;


import com.affairs.teacher.entity.Teacher;
import com.affairs.teacher.service.ITeacherService;
import com.affaris.common.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R login(@RequestBody Teacher teacher) {
        if (teacher == null) {
            return R.failed("提交的内容经解析后为null");
        }
        String teaId = "tea_id";
        String teaPassword = "tea_password";
        // 构造条件进行查询
        Teacher teacherServiceOne = teacherService.getOne(new QueryWrapper<Teacher>().select(teaId, teaPassword).eq(teaId, teacher.getTeaId()));
        if (teacherServiceOne != null) {
            if (teacher.getTeaPassword().equals(teacherServiceOne.getTeaPassword())) {
                return R.success();
            } else {
                return R.failed("密码有误");
            }
        }
        return R.failed("查无此人");
    }
}
