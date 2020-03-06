package com.llb.service.impl;

import com.llb.entity.Student;
import com.llb.mapper.StudentMapper;
import com.llb.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学员信息表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
