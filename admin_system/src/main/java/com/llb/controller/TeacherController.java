package com.llb.controller;


import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;

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

    /**
     * 展示首页
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("teacher/index");
        return modelAndView;
    }
    
    @RequestMapping(value = "/student_administer")
    public ModelAndView information(String teaId) {
    	System.out.println(studentService.findTeaTwoById(teaId));
        ModelAndView modelAndView = new ModelAndView("teacher/student_administer");
        modelAndView.addObject("list", studentService.findTeaTwoById(teaId));
        return modelAndView;
    }
}

