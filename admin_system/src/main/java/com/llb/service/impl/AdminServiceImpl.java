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

    @Override
    public Admin findAdminById(String adminId) {
        return adminMapper.findAdminById(adminId);
    }

    @Override
    public Admin findAdminByEmail(String email) {
        return adminMapper.findAdminByEmail(email);
    }

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
}
