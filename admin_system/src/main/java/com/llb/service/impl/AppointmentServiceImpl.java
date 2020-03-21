package com.llb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public IPage<Map<String, Object>> findAppointByStuId(Page<Map<String, Object>> pageParam, String stuId,
                                                         String appointmentStart,
                                                         String appointmentEnd,
                                                         String subject,
                                                         String teaName) {
        return appointmentMapper.findAppointByStuId(pageParam, stuId, appointmentStart, appointmentEnd, subject, teaName);
    }

    /**
     * 根据学员id查找预约列表
     * @param stuId
     * @return
     */
    @Override
    public List<Map<String, Object>> findAppointListByStuId(String stuId) {
        return appointmentMapper.findAppointByStuId(stuId);
    }

    /**
     * 根据预约记录id修改状态
     * @param id
     * @param appointmentFlag
     */
    @Override
    public void editAppointFlag(String id, Integer appointmentFlag) {
        appointmentMapper.editAppointFlag(id, appointmentFlag);
    }
}
