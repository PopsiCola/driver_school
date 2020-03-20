package com.llb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    IPage<Map<String,Object>> findAppointByStuId(Page<Map<String, Object>> pageParam, @Param("stuId") String stuId);

    List<Map<String, Object>> findAppointByStuId(String stuId);
}
