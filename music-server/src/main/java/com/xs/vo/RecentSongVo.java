package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class RecentSongVo implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 歌手id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long singerId;

    /**
     * 歌名
     */
    private String name;

    /**
     * 歌手名
     */
    private String singerName;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 歌曲图片
     */
    private String pic;

    /**
     * 歌词
     */
    private String lyric;

    /**
     * 歌曲地址
     */
    private String url;

    /**
     * 播放量
     */
    private Integer playCount;

    @Serial
    private static final long serialVersionUID = 1L;
}
