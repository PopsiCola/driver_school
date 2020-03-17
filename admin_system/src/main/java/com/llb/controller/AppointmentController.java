package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.llb.entity.Appointment;
import com.llb.service.IAppointmentService;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import com.llb.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 学员-教练 预约练车记录表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-03-17
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    private IStudentService studentService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IAppointmentService appointmentService;

    /**
     * 学员预约教练
     * @param map
     * @return
     */
    @RequestMapping(value = "/appointTeacher", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> appointTeacher(@RequestBody Map<String, String> map,
                                              HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //将用户传来的表单数据转换成实体类
        Appointment appointment = JSONObject.parseObject(JSONObject.toJSONString(map), Appointment.class);
        //截取预约开始、结束时间
        String dateRange = map.get("dateRange");

        //截取开始和结束时间
        String start = dateRange.substring(0, dateRange.length()/2-1);
        String end = dateRange.substring(dateRange.length()/2+2, dateRange.length());
        //补全字段
        appointment.setId(UUID.randomUUID().toString().replaceAll("-", "").trim());
        appointment.setAppointmentStart(start);
        appointment.setAppointmentEnd(end);
        appointment.setDatestamp(new DateUtil().formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        //保存预约信息
        try {
            appointmentService.saveAppointMent(appointment);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 200);
            result.put("msg", "预约信息已经发送！");
            return result;
        }

        result.put("code", 200);
        result.put("msg", "预约信息已经发送！");
        return result;
    }
}

