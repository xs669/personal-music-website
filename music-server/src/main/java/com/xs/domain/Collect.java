package com.xs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏
 * TableName collect
 */
@Data
public class Collect implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 收藏类型(1歌单0歌曲)
     */
    private Integer type;

    /**
     * 歌曲id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songId;

    /**
     * 歌单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    /**
     * 收藏时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}