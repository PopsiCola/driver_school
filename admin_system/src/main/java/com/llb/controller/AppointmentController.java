package com.llb.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Appointment;
import com.llb.entity.Student;
import com.llb.service.IAppointmentService;
import com.llb.service.IStudentService;
import com.llb.service.ITeacherService;
import com.llb.utils.DateUtil;
import com.llb.web.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

        String dateRange = map.get("dateRange");
        String subject = map.get("subject");
        String teaId = map.get("teaId");
        //不为空
        if("".equals(dateRange)) {
            result.put("code", 201);
            result.put("msg", "请选择预约时间！");
            return result;
        }
        if("".equals(subject)) {
            result.put("code", 201);
            result.put("msg", "请选择预约科目！");
            return result;
        }
        if("".equals(teaId)) {
            result.put("code", 201);
            result.put("msg", "请选择预约教练！");
            return result;
        }

        //将用户传来的表单数据转换成实体类
        Appointment appointment = JSONObject.parseObject(JSONObject.toJSONString(map), Appointment.class);
        //截取预约开始、结束时间
        //截取开始和结束时间
        String start = dateRange.substring(0, dateRange.length()/2-1);
        String end = dateRange.substring(dateRange.length()/2+2, dateRange.length());
        //补全字段
        appointment.setId(UUID.randomUUID().toString().replaceAll("-", "").trim());
        appointment.setAppointmentStart(start);
        appointment.setAppointmentEnd(end);
//        appointment.setCreateDate(new DateUtil().formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        //预约提交后，状态应为待同意  1.待同意 2.已拒绝 3.已批准
        appointment.setAppointmentFlag(1);
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

    /**
     * 根据stuid查询预约记录
     * @param stuId
     * @return
     */
    @RequestMapping("/recordListById")
    @ResponseBody
    public Map<String, Object> recordListById(@RequestParam(required = true) String stuId,
                                              String start,
                                              String end,
                                              String subject,
                                              String teaName,
                                              @RequestParam(defaultValue = "1", required = false, value = "page") Integer page,
                                              @RequestParam(defaultValue = "5", required = false, value = "limit") Integer limit) {
        Map<String, Object> result = new HashMap<>();

        //分页操作
        Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, limit);

        //查询学员预约记录
        IPage<Map<String, Object>> appoints = appointmentService.findAppointByStuId(pageParam, stuId, start, end, subject, teaName);
        if(appoints.getTotal() == 0) {
            result.put("code", 201);
            result.put("msg", "没有预约记录！");
            return result;
        }

        result.put("data", appoints.getRecords());
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("count", appoints.getTotal());
        return result;
    }
    
    /**
     * 根据教练id查询预约记录
     */
    
    @RequestMapping(value = "/appointment_teaId")
    @ResponseBody
    public Message student_order(String teaId,String bm_date,
    		@RequestParam(defaultValue = "1", required = false, value = "page") Integer page,
            @RequestParam(defaultValue = "5", required = false, value = "size") Integer size) {
    	System.out.println(teaId);
    	String start_time=null;
    	String End_time = null;
    	if (bm_date == null || bm_date == "") {
    		bm_date = null;
    	}else {
    		System.out.println("sdgsghsh");
    		System.out.println(bm_date.substring(0,10));
    		start_time=bm_date.substring(0,10);
    		System.out.println(bm_date.substring(13,23));
    		End_time = bm_date.substring(13,23);
		}
//    	if (bm_date != "") {
//    		System.out.println("sdgsghsh");
//    		System.out.println(bm_date.substring(0,10));
//    		start_time=bm_date.substring(0,10);
//    		System.out.println(bm_date.substring(13,23));
//    		End_time = bm_date.substring(13,23);
//    	}
    	Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, size);
    	IPage<Map<String, Object>> student_map = appointmentService.appointment_teaId(pageParam, teaId, start_time, End_time);
    	System.out.println(student_map.getRecords());
    	Message me= new Message();
    	me.put("data", student_map.getRecords()) ;
    	me.put("code", 200);
		me.put("msg", "查询成功");
		me.put("count",student_map.getTotal());
    	return me;
    }

    /**
     * 学员取消预约
     * @return
     */
    @RequestMapping("/cancle")
    @ResponseBody
    public Map<String, Object> cancleAppoint(@RequestBody Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();

        if("3".equals(map.get("appointmentFlag"))) {
            result.put("code", 201);
            result.put("msg", "已取消，不能再次取消！");
            return result;
        } else if ("4".equals(map.get("appointmentFlag"))) {
        	result.put("code", 201);
            result.put("msg", "已拒绝，不能再次取消！");
            return result;
		} else if ("5".equals(map.get("appointmentFlag"))) {
            result.put("code", 201);
            result.put("msg", "已完成，不能再次取消！");
            return result;
        }
        
        appointmentService.editAppointFlag(map.get("id"), 3);
        result.put("code", 200);
        result.put("msg", "取消预约成功！");
        return result;
    }
    
    /**
     * 教练拒绝预约
     * @return
     */
    @RequestMapping("/teaCancle")
    @ResponseBody
    public Map<String, Object> cancleTeaCancleAppoint(@RequestBody Map<String, String> map) {
    	Map<String, Object> result = new HashMap<>();
    	System.out.println(map.get("appointmentFlag")+"!!!!!");
    	if("3".equals(map.get("appointmentFlag"))) {
    		result.put("code", 201);
    		result.put("msg", "已取消，不能再次取消！");
    		return result;
    	}else if ("4".equals(map.get("appointmentFlag"))) {
        	result.put("code", 201);
            result.put("msg", "已拒绝，不能再次拒绝！");
            return result;
		}else if ("2".equals(map.get("appointmentFlag"))) {
	    	result.put("code", 201);
	    	result.put("msg", "已同意预约！");
	    	return result;
		}else if ("5".equals(map.get("appointmentFlag"))) {
	    	result.put("code", 201);
	    	result.put("msg", "练车已完成，不可修改状态！");
	    	return result;
		}else if ("同   意 ".equals(map.get("appointmentFlag"))) {
		appointmentService.editAppointFlag(map.get("id"), 2);
        Appointment appointment = appointmentService.findApponitById(map.get("id"));
        Student student = new Student();
        student.setStuId(appointment.getStuId());
        student.setStuTwo(appointment.getTeaId());
        studentService.editStudent(student);
        result.put("code", 200);
    	result.put("msg", "同意预约！");
    	return result;
		}
    	
    	appointmentService.editAppointFlag(map.get("id"), 4);
    	result.put("code", 200);
    	result.put("msg", "拒绝预约成功！");
    	return result;
    }

    /**
     * 学员评价(评星，评论)
     * @param map
     * @return
     */
    @RequestMapping(value = "/stuContent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> stuContent(@RequestBody Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(map);
        //获取id，根据id查询评论信息
        Appointment apponit = appointmentService.findApponitById(map.get("id"));
        System.out.println("/////////");
        String xypl = map.get("xypl");
        if (xypl== null) {
        	xypl = "";
		}
        System.out.println(xypl);
        System.out.println(apponit);
        System.out.println(apponit.getAppointmentFlag());
        if (xypl.equals("1")) {
        	result.put("code", 201);
            result.put("msg", "学员未评论，无法回复！");
            return result;
		}else if(xypl.equals("2")){
			
	        //判断该评论是否不存在
	        if(apponit == null) {
	            result.put("code", 201);
	            result.put("msg", "该条评论不存在！");
	            return result;
	        }
	
	        //进行逻辑判断，当预约状态为3是学员才能进行评星、评价
	        else if(5 == apponit.getAppointmentFlag()) {
	        	//符合评价操作
	            apponit.setTeaContent(map.get("teaContent"));
	            appointmentService.editAppoint(apponit);
	
	            result.put("code", 200);
	            result.put("msg", "评论成功！");
	        }
	
	        //当有评星或评价时，不能进行重复评论操作
	        else if(apponit.getStuContent() != null || apponit.getStuStar() != null) {
	            result.put("code", 201);
	            result.put("msg", "您已对该次练车进行过评价，不重复评价！");
	            return result;
	        }else {
	        	result.put("code", 201);
	            result.put("msg", "练车未结束，不能进行评价！");
	            return result;
			}
		}else {
			
	        //判断该评论是否不存在
	        if(apponit == null) {
	            result.put("code", 201);
	            result.put("msg", "该条评论不存在！");
	            return result;
	        }
	
	        //进行逻辑判断，当预约状态为3是学员才能进行评星、评价
	        else if(5 == apponit.getAppointmentFlag()) {
	        	//符合评价操作
	            apponit.setStuStar(Integer.parseInt(map.get("star")));
	            apponit.setStuContent(map.get("stuContent"));
	            appointmentService.editAppoint(apponit);
	
	            result.put("code", 200);
	            result.put("msg", "评论成功！");
	        }
	
	        //当有评星或评价时，不能进行重复评论操作
	        else if(apponit.getStuContent() != null || apponit.getStuStar() != null) {
	            result.put("code", 201);
	            result.put("msg", "您已对该次练车进行过评价，不重复评价！");
	            return result;
	        }else {
	        	result.put("code", 201);
	            result.put("msg", "练车未结束，不能进行评价！");
	            return result;
			}
		}
        return result;
    }

}

