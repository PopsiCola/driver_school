package com.llb.service;

import com.llb.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 学员信息表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface IStudentService extends IService<Student> {

    /**
     * 根据学员名查询学员
     * @param stuId
     * @return
     */
    Student findStudentById(String stuId);

    /**
     * 修改学院账号信息
     * @param student
     * @return
     */
//    Map<String, Object> updateStudent(Student student);

    /**
     * 根据邮箱查询学员
     * @param email
     * @return
     */
    Student findStuByEmail(String email);

    /**
     * 修改学员密码
     * @param stuEmail
     * @param stuPwd
     * @return
     */
    Map<String, Object> editStuPwd(String stuEmail, String stuPwd);
}
