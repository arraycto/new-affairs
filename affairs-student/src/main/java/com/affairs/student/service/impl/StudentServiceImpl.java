package com.affairs.student.service.impl;

import com.affairs.student.entity.Student;
import com.affairs.student.mapper.StudentMapper;
import com.affairs.student.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生信息 服务实现类
 * </p>
 *
 * @author Vulgarities
 * @since 2020-06-25
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
