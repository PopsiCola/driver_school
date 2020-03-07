package com.llb.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学员唯一标识
     */
    @TableId("stu_id")
    private String stuId;

    /**
     * 学员姓名
     */
    @TableField("stu_name")
    private String stuName;

    /**
     * 性别 1男 0女
     */
    @TableField("stu_sex")
    private Integer stuSex;

    /**
     * 学员年龄
     */
    @TableField("stu_age")
    private Integer stuAge;

    /**
     * 学员家庭地址
     */
    @TableField("stu_address")
    private String stuAddress;

    /**
     * 学员联系电话
     */
    @TableField("stu_phone")
    private String stuPhone;

    /**
     * 学员报名时间
     */
    @TableField("stu_createdate")
    private LocalDateTime stuCreatedate;


}
