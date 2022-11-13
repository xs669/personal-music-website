package com.xs.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SearchDto implements Serializable {

    /**
     * 指定用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 搜索关键字
     */
    private String keyWord;

    @Serial
    private static final long serialVersionUID = 1L;

}
