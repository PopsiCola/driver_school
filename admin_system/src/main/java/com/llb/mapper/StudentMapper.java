package com.llb.mapper;

import com.llb.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 学员信息表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface StudentMapper extends BaseMapper<Student> {

    Student findStudentById(String stuId);
}
