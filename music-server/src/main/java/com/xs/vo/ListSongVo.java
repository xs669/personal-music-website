package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ListSongVo implements Serializable {

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

    /**
     * 歌手名-歌曲名
     */
    private String singerNameAndsongName;

    @Serial
    private static final long serialVersionUID = 1L;
}
