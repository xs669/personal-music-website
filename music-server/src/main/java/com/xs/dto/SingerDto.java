package com.xs.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SingerDto implements Serializable {

    /**
     * 歌手性别
     */
    private Integer sex;

    /**
     * 按歌手性别统计的数量
     */
    private Integer number;

    @Serial
    private static final long serialVersionUID = 1L;
}
