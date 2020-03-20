package com.llb.service;

import com.llb.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llb.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员-教练 预约练车记录表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-03-17
 */
public interface IAppointmentService extends IService<Appointment> {

    /**
     * 保存预约记录
     * @param appointment
     */
    void saveAppointMent(Appointment appointment);

    /**
     * 根据学员id查找预约记录
     * @param stuId
     * @return
     */
    List<Map<String, String>> findAppointByStuId(String stuId);

}
