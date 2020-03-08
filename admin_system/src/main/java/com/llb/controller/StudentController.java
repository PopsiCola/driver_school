package com.llb.controller;


import com.llb.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping(value="/index", method = RequestMethod.POST)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("student/index");
        return modelAndView;
    }

}

