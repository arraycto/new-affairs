package com.affairs.killers.controller;

import com.affairs.killers.service.IKillersService;
import com.affaris.common.to.ElectiveTo;
import com.affaris.common.utils.R;
import com.affaris.common.vo.StudentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Vulgarities
 */
@RequestMapping("/killers/killers")
@RestController
public class KillersController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IKillersService killersService;

    /**
     * 抢课
     *
     * @return
     */
    @RequestMapping("kill")
    public R kill(@RequestBody ElectiveTo electiveTo, HttpSession session) {
        // 当前登录的学生信息
        StudentVo studentVo = (StudentVo) session.getAttribute("studentVo");
        if (studentVo != null) {
            // 学号
            Integer stuId = studentVo.getStuId();
            // 课程号
            Integer couId = electiveTo.getCouId();
            // 教师号
            Integer teaId = electiveTo.getTeaId();
            // 随机码
            String randomCode = electiveTo.getRandomCode();
            boolean flag = killersService.kill(stuId, couId, teaId, randomCode);
            if (!flag) {
                return R.failed("加入失败！");
            }
        } else {
            return R.failed("你的会话已过期，请重新登录");
        }
        return R.success();
    }
}
