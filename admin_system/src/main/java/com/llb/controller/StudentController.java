package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Appointment;
import com.llb.entity.Student;
import com.llb.entity.Teacher;
import com.llb.service.IAppointmentService;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import com.llb.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.*;
import java.util.function.Consumer;

/**
 * <p>
 * 学员信息表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private MailService mailService;
    @Autowired
    private IAppointmentService appointmentService;

    /**
     * 展示学员首页
     * @return
     */
    @RequestMapping(value="/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("student/index");
        return modelAndView;
    }

    /**
     * 显示学员修改信息页
     * @return
     */
    @RequestMapping(value ="/student-info")
    public ModelAndView studentInfo() {
        ModelAndView modelAndView = new ModelAndView("student/student-info");
        return modelAndView;
    }

    /**
     * 显示学员修改密码页
     * @return
     */
    @RequestMapping("/showStuEditPwd")
    public ModelAndView showStuEditPwd() {
        ModelAndView modelAndView = new ModelAndView("student/editPassword");
        return modelAndView;
    }

    /**
     * 编辑学员信息
     * @param stuInfo 前端表单中填写的的数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/editStudentInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editStudentInfo(@RequestBody Map<String, String> stuInfo,
                                               HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //将用户传来的表单数据转换成实体类
        Student student = JSONObject.parseObject(JSONObject.toJSONString(stuInfo), Student.class);
        String stuEmail = stuInfo.get("stuEmail");
        String stuId = stuInfo.get("stuId");
        //验证密码是否正确
        result = studentService.verifyPwd(stuId, stuInfo.get("stuPwd"));
        //密码正确
        if("200".equals(result.get("code").toString())) {
            //修改学员信息
            studentService.editStudent(student);
            //将student重新缓存到session
            student = studentService.findStuByEmail(stuEmail);
            System.out.println(student);
            request.getSession().setAttribute("student", student);
            result.put("code", 200);
            result.put("msg", "修改成功");
            return result;
        }
        return result;
    }

    /**
     * 学员修改密码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/editSutPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editSutPwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //将用户传来的表单数据转换成实体类
        Student newUser = JSONObject.parseObject(JSONObject.toJSONString(map), Student.class);
        String stuNewPwd = map.get("stuNewPwd");
        Student student = studentService.findStuById(newUser.getStuId());
        //既然能够给到stuId，那么用户就是存在的
        if(student == null) {
            result.put("code", 202);
            result.put("msg", "用户不存在！");
            return result;
        }
        //判断学员密码是否正确
        if(!newUser.getStuPwd().equals(student.getStuPwd())) {
            result.put("code", 201);
            result.put("msg", "密码错误！");
            return result;
        }
        //修改密码
        newUser.setStuPwd(stuNewPwd);
        studentService.editStudent(newUser);

        result.put("code", 200);
        result.put("msg", "修改密码成功！");
        return result;
    }

    /**
     * 显示邮箱找回密码界面
     * @return
     */
    @RequestMapping("/found_password")
    public ModelAndView foundPassword(){
        ModelAndView modelAndView = new ModelAndView("student/found_password");
        return modelAndView;
    }

    /**
     * 发送学员验证码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getCode")
    public Map<String, Object> sendForgetPwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        final HttpSession httpSession = request.getSession();
        Map<String, Object> result = new HashMap<>();
        //获取学员id
        String stuId = map.get("stuId");
        //获取邮箱号
        String stuEmail = map.get("stuEmail");
        //验证邮箱号是不是该学员绑定的邮箱号
        Student student = studentService.findStuById(stuId);
        if(!stuEmail.equals(student.getStuEmail())) {
            result.put("code", 200);
            result.put("msg", "邮箱未绑定该账号");
            return result;
        }

        //给邮箱发送验证码
       result = mailService.sendHtmlMail(stuEmail, "忘记密码");

        //将验证码放到浏览器缓存5分钟，5分钟失效
        httpSession.setAttribute("verifyMailCode", result.get("verifyMailCode"));
        //设置过期时间
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
            }, 5*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 201);
            result.put("msg", "发送验证码失败！");
            return result;
        }
        result.put("code", 200);
        result.put("msg", "验证码已发送至邮箱，注意查收！");

        return result;
    }

    /**
     * 通过邮箱验证码来修改密码-学员忘记密码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPwdByMail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editPwdByMail(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        //将用户传来的表单数据转换成实体类
        Student student = JSONObject.parseObject(JSONObject.toJSONString(map), Student.class);
        //获取用户验证码
        String verifyCode = map.get("verifyCode");

        //获取验证码
        Object verifyMailCode = request.getSession().getAttribute("verifyMailCode");
        if("".equals(verifyMailCode) || verifyMailCode == null) {
            result.put("code", 201);
            result.put("msg", "验证码已过期！");
            return result;
        } else if(verifyCode == verifyMailCode) {
            result.put("code", 201);
            result.put("msg", "验证码不正确，请重新输入！");
            return result;
        }

        studentService.editStudent(student);
        result.put("code", 200);
        result.put("msg", "修改密码成功！");
        return result;
    }

    /**
     * 学员预约教练
     * @return
     */
    @RequestMapping("/appointmentCoach")
    public ModelAndView appointmentCoach() {
        ModelAndView modelAndView = new ModelAndView("student/appointmentCoach");

        //学员预约时，需要知道有哪些教练可以预约
        List<Teacher> teacherList = teacherService.findAllTeacher();
        modelAndView.addObject("teacherList", teacherList);
        return modelAndView;
    }

    /**
     * 学员预约教练
     * @return
     */
    @RequestMapping("/appointmentRecord")
    public ModelAndView appointmentRecord() {
        ModelAndView modelAndView = new ModelAndView("student/appointmentRecord");
        return modelAndView;
    }

    /**
     * 展示学员预约时间线
     * @return
     */
    @RequestMapping("/appointmentTimeline")
    public ModelAndView appointmentTimeline(HttpServletRequest request) {
       ModelAndView modelAndView = new ModelAndView("student/appointment_timeline");
        Student student = (Student) request.getSession().getAttribute("student");

        //查询学员预约记录
        List<Map<String, Object>> list = appointmentService.findAppointListByStuId(student.getStuId());

        System.out.println(list);
        modelAndView.addObject("appointList", list);

        return modelAndView;
    }

}

