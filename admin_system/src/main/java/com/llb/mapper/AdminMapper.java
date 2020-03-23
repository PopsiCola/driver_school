package com.llb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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

    /**
     * 查询所有管理员
     * @param pageParam
     * @param account
     * @return
     */
    IPage<Map<String,Object>> findAllAdmin(Page<Map<String, Object>> pageParam, @Param("account") String account);

    /**
     * 根据id删除管理员
     * @param adminId
     */
    void deleteAdmin(String adminId);
}