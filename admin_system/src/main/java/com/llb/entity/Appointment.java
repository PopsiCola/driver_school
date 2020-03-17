package com.llb.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学员-教练 预约练车记录表
 * </p>
 *
 * @author llb
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学员预约练车表主键
     */
    private String id;

    /**
     * 学员id主键
     */
    private String stuId;

    /**
     * 教练id主键
     */
    private String teaId;

    /**
     * 预约科目
     */
    private String subject;

    /**
     * 学员预约开始时间
     */
    private String appointmentStart;

    /**
     * 学员预约结束时间
     */
    private String appointmentEnd;

    /**
     * 学员上车时间
     */
    private String boardingTime;

    /**
     * 学员下车时间
     */
    private String alightingTime;

    /**
     * 学员评星。1-5星
     */
    private Integer stuStar;

    /**
     * 学员评论
     */
    private String stuContent;

    /**
     * 教练评论
     */
    private String teaContent;

    /**
     * 预约状态。1：学员预约，教练未同意。2：学员预约，教练同意
     */
    private String appointmentFlag;

    /**
     * 时间戳
     */
    private String datestamp;

}
