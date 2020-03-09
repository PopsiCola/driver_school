package com.llb.controller;

import com.llb.entity.Admin;
import com.llb.entity.Student;
import com.llb.entity.Teacher;
import com.llb.service.IAdminService;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * @Author llb
 * Date on 2020/3/4
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IStudentService studentService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IAdminService adminService;

    /**
     * 显示登录页
     * @return
     */
    @RequestMapping("/index")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录 用户/教练/管理员 登录
     * @param role 类型:1.用户 2.管理员 3.教练
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> map, HttpServletRequest request) {
        //账号密码是否正确标志
        boolean flag = false;

        String account = map.get("account");
        String password = map.get("password");
        String role = map.get("role");

        Student student = null;
        Admin admin = null;
        Teacher teacher = null;

        if("1".equals(role)) {
            student = studentService.findStudentById(account);
            if(student != null && student.getStuPwd().equals(password)) {
                flag = true;
                request.getSession().setAttribute("student", student);
            }
        } else if("2".equals(role)) {
            admin = adminService.findAdminById(account);
            if(admin != null && admin.getAdminPwd().equals(password)) {
                flag = true;
                request.getSession().setAttribute("admin", admin);
                System.out.println(admin);
            }
        } else {
            teacher = teacherService.findTeacherById(account);
            if(teacher != null && teacher.getTeaPwd().equals(password)) {
                flag = true;
                request.getSession().setAttribute("teacher", teacher);
            }
        }

        //封装返回结果
        Map<String, Object> result = new HashMap<>();
        if(flag) {
            result.put("code", 200);
            result.put("role", role);
        } else{
            result.put("code", 201);
            result.put("msg", "用户名或密码错误!");
        }
        return result;
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/loginOut")
    public String loginOut(String role, HttpServletRequest request) {
        //判断是哪个角色，注销登录
        if("1".equals(role)) {
            request.getSession().removeAttribute("student");
        } else if("2".equals(role)) {
            request.getSession().removeAttribute("admin");
        } else {
            request.getSession().removeAttribute("teacher");
        }
        //获取session
        return "login";
    }
}
