package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchVo implements Serializable {

    /**
     * 指定字段id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 搜索关键字
     */
    private String keyWord;

    /**
     * id类型 (0:用户, 1:歌曲, 2:歌单)
     */
    private Integer type;
}
