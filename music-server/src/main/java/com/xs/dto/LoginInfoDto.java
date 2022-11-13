package com.xs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录账号密码
 */
@NoArgsConstructor
@Data
public class LoginInfoDto implements Serializable {

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
