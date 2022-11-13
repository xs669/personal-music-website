package com.xs.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ListSongByNameDto implements Serializable {

    /**
     * 歌单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    /**
     * 歌单歌曲搜索关键词
     */
    private String name;

    @Serial
    private static final long serialVersionUID = 1L;
}
