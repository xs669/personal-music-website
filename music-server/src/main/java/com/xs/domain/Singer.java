package com.xs.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 歌手
 * TableName singer
 */
@Data
public class Singer implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别(1男0女)
     */
    private Integer sex;

    /**
     * 头像
     */
    private String pic;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 所属地区
     */
    private String location;

    /**
     * 简介
     */
    private String introduction;

    @Serial
    private static final long serialVersionUID = 1L;
}