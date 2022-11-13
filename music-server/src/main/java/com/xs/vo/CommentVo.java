package com.xs.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentVo implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    /**
     * 评论点赞数
     */
    private Integer up;

    @Serial
    private static final long serialVersionUID = 1L;

}
