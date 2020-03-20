package com.llb.mapper;

import com.llb.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员-教练 预约练车记录表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-03-17
 */
public interface AppointmentMapper extends BaseMapper<Appointment> {

    void saveAppointMent(Appointment appointment);

    List<Map<String, String>> findAppointByStuId(String stuId);
}
