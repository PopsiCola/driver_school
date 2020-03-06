package com.llb.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教练员表
 * </p>
 *
 * @author llb
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教练唯一标识
     */
    private String teaId;

    /**
     * 教练姓名
     */
    private String teaName;

    /**
     * 性别 1:男 0:女
     */
    private Integer teaSex;

    /**
     * 教练年龄
     */
    private Integer teaAge;

    /**
     * 教练地址
     */
    private String teaAddress;

    /**
     * 教练联系方式
     */
    private String teaPhone;

    /**
     * 创建时间
     */
    private LocalDateTime teaCreatdate;


}
