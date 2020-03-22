package com.llb.mapper;

import com.llb.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
public interface AdminMapper extends BaseMapper<Admin> {

    Admin findAdmin(String account);

    Admin findAdmin(@Param("account") String account, @Param("email") String email);

    Admin findAdminByAccAndMail(@Param("account") String account, @Param("email") String email);

    Admin findAdminByEmail(String adminEmail);

    void updateAdminPwd(@Param("adminEmail") String adminEmail, @Param("adminPwd") String adminPwd);

    void saveAdmin(Admin admin);

    Admin findAdminById(String adminId);

    void updateAdmin(Admin admin);
}