package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CollectVo implements Serializable {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 收藏类型
     */
    private Integer type;

    /**
     * 歌曲id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songId;

    /**
     * 歌曲id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    @Serial
    private static final long serialVersionUID = 1L;

}
