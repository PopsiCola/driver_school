package com.llb.service;

import com.llb.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
