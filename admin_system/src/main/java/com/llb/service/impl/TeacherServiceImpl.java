package com.llb.service.impl;

import com.llb.entity.Teacher;
import com.llb.mapper.TeacherMapper;
import com.llb.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
