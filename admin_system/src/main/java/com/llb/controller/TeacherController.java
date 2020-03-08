package com.llb.controller;


import com.llb.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 展示首页
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("teacher/index");
        return modelAndView;
    }

}

