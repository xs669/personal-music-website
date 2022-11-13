package com.xs.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SongDto implements Serializable {

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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

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
