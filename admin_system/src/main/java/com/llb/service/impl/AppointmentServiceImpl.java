package com.llb.service.impl;

import com.llb.entity.Appointment;
import com.llb.mapper.AppointmentMapper;
import com.llb.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员-教练 预约练车记录表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-03-17
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
     * 保存预约记录
     * @param appointment
     */
    @Override
    public void saveAppointMent(Appointment appointment) {
        appointmentMapper.saveAppointMent(appointment);
    }

    /**
     * 根据学员id查找预约记录
     * @param stuId
     * @return
     */
    @Override
    public Map<String, Object> findAppointByStuId(String stuId) {
        Map<String, Object> result = new HashMap<>();
        List<Appointment> appointList = appointmentMapper.findAppointByStuId(stuId);
        if(appointList == null) {
            result.put("code", 201);
            result.put("msg", "没有预约记录！");
            return result;
        }

        result.put("data", appointList);
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("count", appointList.size());
        return result;
    }
}
