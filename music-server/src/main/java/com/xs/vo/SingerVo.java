package com.xs.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SingerVo implements Serializable {

    /**
     * 歌手所在地区
     */
    private String location;

    /**
     * 按地区分组查询歌手数量
     */
    private Integer number;

    @Serial
    private static final long serialVersionUID = 1L;
}
