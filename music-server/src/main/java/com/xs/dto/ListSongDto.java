package com.xs.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ListSongDto implements Serializable {

    /**
     * 歌曲id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songId;

    /**
     * 歌曲名称
     */
    private String songName;

    @Serial
    private static final long serialVersionUID = 1L;
}
