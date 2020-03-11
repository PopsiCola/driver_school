package com.llb.mapper;

import com.llb.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教练员表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Teacher findTeacher(String account);

    Teacher findTeacher(@Param("account") String account, @Param("email") String email);

    Teacher findTeaByAccAndPwd(@Param("account") String account, @Param("email") String email);

    Teacher findTeacherByEmail(String teaEmail);

    void updateTeaPwd(String teaEmail, String teacherPwd);

    void saveTeacher(Teacher teacher);
}
