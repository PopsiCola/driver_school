package com.llb.service;

import com.llb.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llb.mapper.TeacherMapper;

import java.util.Map;

/**
 * <p>
 * 教练员表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * 根据教练名查询教练
     * @param teaId
     * @return
     */
    Teacher findTeacherById(String teaId);

    /**
     * 根据邮箱查询教练
     * @param teaEmail
     * @return
     */
    Teacher findTeacherByEamil(String teaEmail);

    /**
     * 修改教练密码
     * @param teaEmail
     * @param teaPwd
     * @return
     */
    Map<String, Object> editTeaPwd(String teaEmail, String teaPwd);
}
