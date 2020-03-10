package com.llb.service;

import com.llb.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 根据管理员名称查询管理员
     * @param adminName
     * @param password
     * @return
     */
    Admin findAdminById(String adminName);

    /**
     * 根据管理员邮箱查询管理员
     * @param email
     * @return
     */
    Admin findAdminByEmail(String email);

    /**
     * 修改管理员密码
     * @param email
     * @param adminPwd
     * @return
     */
    Map<String, Object> editAdminPwd(String email, String adminPwd);
}
