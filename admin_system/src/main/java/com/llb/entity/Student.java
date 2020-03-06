package com.llb.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学员信息表
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学员唯一标识
     */
    private String stuId;

    /**
     * 学员姓名
     */
    private String stuName;

    /**
     * 性别 1男 0女
     */
    private Integer stuSex;

    /**
     * 学员年龄
     */
    private Integer stuAge;

    /**
     * 学员家庭地址
     */
    private String stuAddress;

    /**
     * 学员联系电话
     */
    private String stuPhone;

    /**
     * 学员报名时间
     */
    private LocalDateTime stuCreatedate;


}
