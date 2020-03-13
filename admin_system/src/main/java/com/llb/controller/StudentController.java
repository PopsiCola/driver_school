package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Student;
import com.llb.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.method.support.ModelAndViewContainer;
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




}

