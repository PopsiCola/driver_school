package com.llb.service.impl;

import com.llb.entity.Student;
import com.llb.entity.Teacher;
import com.llb.mapper.TeacherMapper;
import com.llb.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 教练员表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher findTeacherById(String teaId) {
        return teacherMapper.findTeacherById(teaId);
    }

    @Override
    public Teacher findTeacherByEamil(String teaEmail) {
        return teacherMapper.findTeacherByEmail(teaEmail);
    }

    @Override
    public Map<String, Object> editTeaPwd(String teaEmail, String teaPwd) {
        Map<String, Object> result = new HashMap<>();
        //先通过邮箱查询是否已经注册
        Teacher teacher = findTeacherByEamil(teaEmail);
        //未注册
        if(teacher == null) {
            result.put("code", 200);
            result.put("msg", "该邮箱没有被注册，请先注册！");
            return result;
        }
        teacherMapper.updateTeaPwd(teaEmail, teaPwd);
        result.put("code", 200);
        result.put("msg", "密码修改成功！");
        return result;
    }
}
