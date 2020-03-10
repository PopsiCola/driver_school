package com.llb.service.impl;

import com.llb.entity.Student;
import com.llb.mapper.StudentMapper;
import com.llb.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Student findStudentById(String stuId) {
        return studentMapper.findStudentById(stuId);
    }

    @Override
    public Student findStuByEmail(String email) {
        return studentMapper.findStuByEmail(email);
    }

    @Override
    public Map<String, Object> editStuPwd(String stuEmail, String stuPwd) {
        Map<String, Object> result = new HashMap<>();
        //先通过邮箱查询是否已经注册
        Student student = findStuByEmail(stuEmail);
        //未注册
        if(student == null) {
            result.put("code", 200);
            result.put("msg", "该邮箱没有被注册，请先注册！");
            return result;
        }
        studentMapper.updateStuPwd(stuEmail, stuPwd);
        result.put("code", 200);
        result.put("msg", "密码修改成功！");
        return result;
    }
}
