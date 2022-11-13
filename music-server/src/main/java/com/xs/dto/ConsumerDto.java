package com.xs.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ConsumerDto implements Serializable {

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 按用户性别统计的数量
     */
    private Integer number;

    @Serial
    private static final long serialVersionUID = 1L;
}
