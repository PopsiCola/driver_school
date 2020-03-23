package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Student;
import com.llb.entity.Teacher;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import com.llb.service.MailService;
import com.llb.web.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 * 教练员表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;
    
    @Autowired
    private IStudentService studentService;

    @Autowired
    private MailService mailService;

    /**
     * 展示首页
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("teacher/index");
        return modelAndView;
    }

    /**
     * 教练查看学员列表
     * @param teaId
     * @param stu_name
     * @return
     */
    @RequestMapping(value = "/student_administer")
    public ModelAndView student_administer(String teaId,String stu_name) {
        ModelAndView modelAndView = new ModelAndView("teacher/student_administer");
        modelAndView.addObject("teaId", teaId);
//        modelAndView.addObject("student_list", studentService.findTeaTwoById(teaId,stu_name));
        return modelAndView;
    }
    
    /**
     * 教练查看预约记录
     * @param teaId
     * @param stu_name
     * @return
     */
    @RequestMapping(value = "/student_appointment")
    public ModelAndView student_appointment(String teaId,String stu_name) {
    	ModelAndView modelAndView = new ModelAndView("teacher/student_appointment");
    	modelAndView.addObject("teaId", teaId);
//        modelAndView.addObject("student_list", studentService.findTeaTwoById(teaId,stu_name));
    	return modelAndView;
    }
    
    //根据教练id查询学员表
    @RequestMapping(value = "/student_two_id")
    @ResponseBody
    public Message student_two_id(String teaId,String stu_name,String bm_date,
    		@RequestParam(defaultValue = "1", required = false, value = "page") Integer page,
            @RequestParam(defaultValue = "5", required = false, value = "limit") Integer limit) {
    	System.out.println("sdgsghsh");
    	String start_time=null;
    	String End_time = null;
    	if (bm_date != "") {
    		System.out.println("sdgsghsh");
    		System.out.println(bm_date.substring(0,10));
    		start_time=bm_date.substring(0,10);
    		System.out.println(bm_date.substring(13,23));
    		End_time = bm_date.substring(13,23);
		}
    	Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, limit);
    	IPage<Map<String, Object>> student_map = studentService.findTeaTwoById(pageParam,teaId,stu_name,start_time,End_time);
    	System.out.println(student_map.getRecords());
    	Message me= new Message();
    	me.put("data", student_map.getRecords()) ;
    	me.put("code", 200);
		me.put("msg", "查询成功");
		me.put("count",student_map.getTotal());
    	return me;
    }
    

    /**
     * 展示教练信息
     * @return
     */
    @RequestMapping("teacherInfo")
    public ModelAndView teacherInfo() {
        ModelAndView modelAndView = new ModelAndView("teacher/teacher_info");
        return modelAndView;
    }

    /**
     * 修改教练信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "editTeacherInfo", method = RequestMethod.POST)
    public Map<String, Object> editTeacherInfo(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        //将用户传来的表单数据转换成实体类
        Teacher newTeacher = JSONObject.parseObject(JSONObject.toJSONString(map), Teacher.class);

        //TODO:通过账号和邮箱查询，判断是否存在，如果存在，则该账号已被注册
        Teacher teacher1 = teacherService.findTeacher(newTeacher.getTeaAccount());
        Teacher teacher2 = teacherService.findTeacher(newTeacher.getTeaEmail());
        if(teacher1 != null) {
            //判断是否是一个账号，如果不是，则该账号已被注册
            if(!teacher1.getTeaId().equals(newTeacher.getTeaId())) {
                result.put("code", 201);
                result.put("msg", "该账号已被注册！");
                return result;
            }
        }
        if(teacher2 != null) {
            //判断是否是一个账号，如果不是，则该邮箱已被绑定
            if(!teacher2.getTeaId().equals(newTeacher.getTeaId())) {
                result.put("code", 201);
                result.put("msg", "该邮箱已被绑定！");
                return result;
            }
        }


        Teacher teacher = teacherService.findTeacherById(newTeacher.getTeaId());
        //既然能够给到stuId，那么用户就是存在的
        if(teacher == null) {
            result.put("code", 202);
            result.put("msg", "用户不存在！");
            return result;
        }

        //判断学员密码是否正确
        if(!newTeacher.getTeaPwd().equals(teacher.getTeaPwd())) {
            result.put("code", 201);
            result.put("msg", "密码错误！");
            return result;
        }

        //修改密码
        teacherService.editTeacher(newTeacher);
        //将教练重新放入缓存中
        teacher = teacherService.findTeacherById(newTeacher.getTeaId());
        request.getSession().setAttribute("teacher", teacher);

        result.put("code", 200);
        result.put("msg", "修改成功");
        return result;
    }

    /**
     * 修改密码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/editTeaPwd", method = RequestMethod.POST)
    public Map<String, Object> editTeaPwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //将用户传来的表单数据转换成实体类
        Teacher newTeacher = JSONObject.parseObject(JSONObject.toJSONString(map), Teacher.class);
        String teaNewPwd = map.get("teaNewPwd");

        Teacher teacher = teacherService.findTeacherById(map.get("teaId"));
        //既然能够给到teaId，那么用户就是存在的
        if(teacher == null) {
            result.put("code", 202);
            result.put("msg", "用户不存在！");
            return result;
        }
        //判断学员密码是否正确
        if(!newTeacher.getTeaPwd().equals(teacher.getTeaPwd())) {
            result.put("code", 201);
            result.put("msg", "密码错误！");
            return result;
        }
        //修改密码
        teacherService.editTeacher(teacher.setTeaPwd(teaNewPwd));
        result.put("code", 200);
        result.put("msg", "修改密码成功！");
        return result;
    }

    /**
     * 通过邮箱修改密码
     * @return
     */
    @RequestMapping("/foundPassword")
    public ModelAndView foundPassword() {
        ModelAndView modelAndView = new ModelAndView("teacher/found_password");
        return modelAndView;
    }

    /**
     * 展示修改密码页面
     * @return
     */
    @RequestMapping("/editPassword")
    public ModelAndView editPassword() {
        ModelAndView modelAndView = new ModelAndView("teacher/editPassword");
        return modelAndView;
    }

    /**
     * 教练发送验证码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCode(@RequestBody Map<String, String> map, HttpServletRequest request) {
        final HttpSession httpSession = request.getSession();
        Map<String, Object> result = new HashMap<>();
        //获取教练id
        String teaId = map.get("teaId");
        //获取邮箱号
        String email = map.get("teaEmail");

        //验证邮箱是否是绑定该账号的邮箱
        Teacher teacher = teacherService.findTeacherById(teaId);
        if(!email.equals(teacher.getTeaEmail())) {
            result.put("code", 200);
            result.put("msg", "邮箱未绑定该账号");
            return result;
        }

        //给邮箱发送验证码
        result = mailService.sendHtmlMail(email, "忘记密码");
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
     * 通过邮箱验证码来修改密码-教练忘记密码
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPwdByMail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editPwdByMail(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        //将用户传来的表单数据转换成实体类
        Teacher teacher = JSONObject.parseObject(JSONObject.toJSONString(map), Teacher.class);
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

        //修改密码
        teacherService.editTeacher(teacher);
        result.put("code", 200);
        result.put("msg", "修改密码成功！");
        return result;
    }


}

