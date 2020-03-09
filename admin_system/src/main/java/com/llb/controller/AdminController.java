package com.llb.controller;


import com.llb.service.IAdminService;
import com.llb.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/index");
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

