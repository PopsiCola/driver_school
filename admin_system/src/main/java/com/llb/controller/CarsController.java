package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Teacher;
import com.llb.service.ITeacherService;
import com.llb.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-04-30
 */
@RestController
@RequestMapping("/cars")
public class CarsController {
    @Autowired
    private ITeacherService teacherService;

    /**
     * 通过邮箱验证码来修改密码-教练忘记密码
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public List<Teacher> editPwdByMail() {
        List<Teacher> list = new ArrayList<Teacher>();
        return teacherService.findAllTeacher();
    }

}

