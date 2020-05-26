package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Cars;
import com.llb.entity.Teacher;
import com.llb.service.ICarsService;
import com.llb.service.ITeacherService;
import com.llb.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Autowired
    private ICarsService carsService;

    /**
     * 通过邮箱验证码来修改密码-教练忘记密码
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public List<Teacher> editPwdByMail() {
        List<Teacher> teachers = teacherService.findAllTeacher();
        return teachers;
    }

    /**
     * 添加车辆信息
     * @return
     */
    @RequestMapping("/addCar")
    @ResponseBody
    public Map<String, Object> addCar(@RequestBody Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Cars car = JSONObject.parseObject(JSONObject.toJSONString(map), Cars.class);
        car.setCarId(UUID.randomUUID().toString().replace("-", ""));
        try {
            carsService.saveCar(car);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", e);
            return result;
        }
        result.put("code", 200);
        result.put("msg", "成功添加车辆信息");
        return result;
    }

}

