package com.xs.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 歌单包含歌曲列表
 * TableName list_song
 */
@Data
public class ListSong implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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

    @Serial
    private static final long serialVersionUID = 1L;
}