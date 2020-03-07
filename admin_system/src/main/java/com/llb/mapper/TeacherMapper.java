package com.llb.mapper;

import com.llb.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 教练员表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Teacher findTeacherById(String teacherName);
}
