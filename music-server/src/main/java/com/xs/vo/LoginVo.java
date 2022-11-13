package com.xs.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @Serial
    private static final long serialVersionUID = 1L;
}
