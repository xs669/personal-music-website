package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SongVo implements Serializable {

    /**
     * 歌手id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 歌曲搜索关键字
     */
    private String name;

    /**
     * 播放量
     */
    private Integer playCount;

    @Serial
    private static final long serialVersionUID = 1L;
}
