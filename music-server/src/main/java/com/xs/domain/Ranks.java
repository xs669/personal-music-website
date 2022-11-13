package com.xs.domain;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 评价
 * TableName ranks
 */
@Data
public class Ranks implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 歌单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consumerId;

    /**
     * 评分
     */
    private Integer score;

    @Serial
    private static final long serialVersionUID = 1L;
}