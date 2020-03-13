package com.llb.mapper;

import com.llb.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学员信息表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface StudentMapper extends BaseMapper<Student> {

    Student findStudent(String account);

    Student findStudent(@Param("account") String account, @Param("email") String email);

    Student findStuByAccAndMail(@Param("account") String account, @Param("email") String email);

    Student findStuByEmail(String stuEmail);

    void updateStuPwd(String stuEmail, String stuPwd);

    void saveStudent(Student student);

    void editStudent(Student student);
}
