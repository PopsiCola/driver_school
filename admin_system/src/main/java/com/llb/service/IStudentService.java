package com.llb.service;

import com.llb.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
