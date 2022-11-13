package com.xs.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 歌单
 * TableName song_list
 */
@Data
public class SongList implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 歌单图片
     */
    private String pic;

    /**
     * 歌单简介
     */
    private String introduction;

    /**
     * 风格
     */
    private String style;

    @Serial
    private static final long serialVersionUID = 1L;
}