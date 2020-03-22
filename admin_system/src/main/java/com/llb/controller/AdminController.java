package com.llb.controller;


import com.llb.entity.Admin;
import com.llb.service.IAdminService;
import com.llb.service.IStudentService;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    /**
     * 展示管理员模块首页
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/index");
        return modelAndView;
    }

    /**
     * 显示管理员修改密码(此处不应该与教练和学员相同，管理员无权通过邮箱修改密码！如果后期逻辑问题，在进行修改)
     * @return
     */
    @RequestMapping(value = "/showEditPwd")
    public ModelAndView showEditPwd() {
        ModelAndView modelAndView = new ModelAndView("admin/editPassword");
        return modelAndView;
    }

    /**
     * 修改管理员密码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/editAdminPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editAdminPwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        Admin admin = adminService.findAdminById(map.get("adminId"));

        //没有该用户，返回提示
        if(admin == null) {
            result.put("code", 201);
            result.put("msg", "没有该管理员，请正规操作！");
            return result;
        }

        //判断密码是否正确，正确则修改密码
        if(!map.get("oldPwd").equals(admin.getAdminPwd())) {
            result.put("code", 201);
            result.put("msg", "密码错误！");
            return result;
        }

        adminService.editAdminPwd(admin.getAdminMail(), map.get("newPwd"));
        result.put("code", 200);
        result.put("msg", "修改成功！");
        return result;
    }

    /**
     * 修改管理员信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/editAdminInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editAdminInfo(@RequestBody Map<String, String> map,
                                             HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        //补全管理员信息
        Admin admin = adminService.findAdminById(map.get("adminId"));

        //没有该用户，返回提示
        if(admin == null) {
            result.put("code", 201);
            result.put("msg", "没有该管理员，请正规操作！");
            return result;
        }

        //判断密码是否正确，正确则修改密码
        if(!map.get("adminPwd").equals(admin.getAdminPwd())) {
            result.put("code", 201);
            result.put("msg", "密码错误！");
            return result;
        }

        admin.setAdminAccount(map.get("adminAccount"));
        admin.setAdminMail(map.get("adminMail"));

        adminService.updateAdmin(admin);

        //修改完成密码后需要重新进行session缓存
        request.getSession().setAttribute("admin", admin);

        result.put("code", 200);
        result.put("msg", "修改信息成功！");
        return result;
    }

    
    @RequestMapping(value = "/admin-list")
    public ModelAndView admin_list() {
    	ModelAndView modelAndView = new ModelAndView("admin-list");
    	return modelAndView;
    }
    
    @RequestMapping(value = "/admin-edit")
    public ModelAndView admin_edit() {
    	ModelAndView modelAndView = new ModelAndView("admin-edit");
    	return modelAndView;
    }
    
    /**
     * 展示管理员信息
     * @return
     */
    @RequestMapping(value = "/admin-info")
    public ModelAndView showInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/admin-info");
        return modelAndView;
    }


}

