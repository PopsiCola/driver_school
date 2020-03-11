package com.llb.service.impl;

import com.llb.entity.Admin;
import com.llb.mapper.AdminMapper;
import com.llb.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据管理员名称或邮箱查询管理员
     * @param account
     * @return
     */
    @Override
    public Admin findAdmin(String account) {
        return adminMapper.findAdmin(account);
    }

    /**
     * 根据管理员名称或邮箱查询管理员
     * @param account
     * @param admin
     * @return
     */
    @Override
    public Admin findAdmin(String account, String email) {
        return adminMapper.findAdmin(account, email);
    }

    /**
     * 根据管理员名称和密码查询管理员
     * @param account
     * @param admin
     * @return
     */
    @Override
    public Admin findAdminByAccAndMail(String account, String admin) {
        return adminMapper.findAdminByAccAndMail(account, admin);
    }

    /**
     * 根据管理员邮箱查询管理员
     * @param email
     * @return
     */
    @Override
    public Admin findAdminByEmail(String email) {
        return adminMapper.findAdminByEmail(email);
    }

    /**
     * 修改管理员密码
     * @param email
     * @param adminPwd
     * @return
     */
    @Override
    public Map<String, Object> editAdminPwd(String email, String adminPwd) {
        Map<String, Object> result = new HashMap<>();
        //先通过邮箱查询是否已经注册
        Admin admin = findAdminByEmail(email);
        //未注册
        if(admin == null) {
            result.put("code", 201);
            result.put("msg", "该邮箱没有被注册，请先注册！");
            return result;
        }
        adminMapper.updateAdminPwd(email, adminPwd);
        result.put("code", 200);
        result.put("msg", "密码修改成功！");
        return result;
    }

    /**
     * 保存管理员
     * @param admin
     */
    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.saveAdmin(admin);
    }
}
