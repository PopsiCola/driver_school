package com.llb.controller;

import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Admin;
import com.llb.entity.Student;
import com.llb.entity.Teacher;
import com.llb.service.IAdminService;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import com.llb.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 登录、注销、修改密码、注册
 * role 类型:1.用户 2.管理员 3.教练
 * 三种角色登录有可以通过账号、邮箱登录，这两个属性必须唯一。
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
    @Autowired
    private MailService mailService;
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
        return "redirect:/login/index";
    }


    /**
     * 重置密码
     * @param map
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> map,
                                             HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        //判断角色类型
        String role = map.get("role");

        //获取页面传来的验证码
        String emailCode = map.get("emailCode");
        //获取验证码
        String verifyMailCode = request.getSession().getAttribute("verifyMailCode").toString();
        System.out.println("邮箱验证码===="+emailCode);
        if(!emailCode.equals(verifyMailCode)) {
            result.put("code", 201);
            result.put("msg", "验证码不正确，请重新输入！");
            return result;
        }

        String email = map.get("mail");
        String password = map.get("password");
        //判断是哪个角色，重置密码
        if("1".equals(role)) {

            //修改密码
            result = studentService.editStuPwd(email, password);
        } else if("2".equals(role)) {
            //修改密码
            result = adminService.editAdminPwd(email, password);
        } else {
            //修改密码
            result = studentService.editStuPwd(email, password);
        }

        return result;
    }

    /**
     * 发送邮箱验证码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sendCode(@RequestBody Map<String, String> map,
                                        HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String email = map.get("email");

        final HttpSession httpSession = request.getSession();

//        String account = map.get("account");
        //判断用户是否填写了账户名称，如果填写了，则显示账户名称，没有则用邮件账户作为用户账户称呼
//        if(account == null || "".equals(account)) {
//            account = email;
//        }
//        String role = map.get("role");
        result = mailService.sendHtmlMail(email, "修改密码");

        //将验证码放到浏览器缓存5分钟，5分钟失效
        httpSession.setAttribute("verifyMailCode", result.get("verifyMailCode"));

        try {
            //设置失效时间
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    httpSession.removeAttribute("verifyMailCode");
                    System.out.println("邮箱验证码缓存信息删除成功");
                    timer.cancel();
                }
            }, 20000);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 201);
            result.put("msg", "发送验证码失败！");
            return result;
        }
        result.put("code", 200);
        result.put("msg", "发送验证码成功！");

        return result;
    }
}
