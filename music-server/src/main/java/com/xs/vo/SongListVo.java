package com.xs.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class SongListVo implements Serializable {

    /**
     * 歌单类型
     */
    private String style;

    /**
     * 按歌单类型分组查询的歌单数量
     */
    private Integer number;

    @Serial
    private static final long serialVersionUID = 1L;
}
