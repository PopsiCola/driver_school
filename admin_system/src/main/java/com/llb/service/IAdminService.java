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
     * 根据管理员名称或邮箱查询管理员
     * @param account
     * @return
     */
    Admin findAdmin(String account);

    /**
     * 根据管理员名称或邮箱查询管理员
     * @param account
     * @param admin
     * @return
     */
    Admin findAdmin(String account, String email);

    /**
     * 根据管理员名称和密码查询管理员
     * @param account
     * @param admin
     * @return
     */
    Admin findAdminByAccAndMail(String account, String admin);

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

    /**
     * 保存管理员
     * @param admin
     */
    void saveAdmin(Admin admin);

    /**
     * 根据管理员id查找密码
     * @param adminId
     * @return
     */
    Admin findAdminById(String adminId);

    /**
     * 修改管理员信息
     * @param admin
     */
    void updateAdmin(Admin admin);
}
