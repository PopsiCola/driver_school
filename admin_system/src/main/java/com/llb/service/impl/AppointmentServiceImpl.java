package com.llb.service.impl;

import com.llb.entity.Appointment;
import com.llb.mapper.AppointmentMapper;
import com.llb.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
