package com.xs.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * JWT工具类
 */
@Component
public class JwtUtils {

	private static long expireTime;

	private static String secretKey;

	@Value("${token.secretKey}")
	public void setSecretKey(String secretKey) {
		JwtUtils.secretKey = secretKey;
	}

	@Value("${token.expireTime}")
	public void setExpireTime(long expireTime) {
		JwtUtils.expireTime = expireTime;
	}

	/**
	 * 判断token是否存在
	 */
	public static boolean judgeTokenIsExist(String token) {
		return token != null && !"".equals(token) && !"null".equals(token);
	}

	/**
	 * 生成token
	 */
	public static String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	/**
	 * 生成带角色权限的token
	 */
	public static String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
		StringBuilder sb = new StringBuilder();
		for (GrantedAuthority authority : authorities) {
			sb.append(authority.getAuthority()).append(",");
		}
		return Jwts.builder()
				.setSubject(subject)
				.claim("authorities", sb)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	/**
	 * 生成自定义过期时间token
	 */
	public static String generateToken(String subject, long expireTime) {
		return Jwts.builder()
				.setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	/**
	 * 获取tokenBody同时校验token是否有效（无效则会抛出异常）
	 */
	public static Claims getTokenBody(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.replace("Bearer", "")).getBody();
	}
}
