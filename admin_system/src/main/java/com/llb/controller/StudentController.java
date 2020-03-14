package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Student;
import com.llb.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping(value = "editSutPwd", method = RequestMethod.POST)
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

}

