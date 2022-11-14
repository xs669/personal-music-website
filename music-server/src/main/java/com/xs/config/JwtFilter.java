package com.xs.config;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.exception.BizException;
import com.xs.util.JacksonUtils;
import com.xs.util.JwtUtils;
import com.xs.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * JWT请求过滤器
 */
public class JwtFilter extends OncePerRequestFilter {

	private static final long expireTime = 24 * 60 * 60 * 1000L;

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		RedisCache redisCache = applicationContext.getBean(RedisCache.class);

		//后台管理路径外的请求直接跳过
		if (!request.getRequestURI().startsWith(request.getContextPath() + "/admin")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwt = request.getHeader("Authorization");
		String username = null;
		Claims claims = null;
		if (JwtUtils.judgeTokenIsExist(jwt)) {
			try {
				claims = JwtUtils.getTokenBody(jwt);
				username = claims.getSubject();
			} catch (Exception e) {
				e.printStackTrace();
				response.setContentType("application/json;charset=utf-8");
				Result result = Result.create(403, "凭证已失效，请重新登录！");
				PrintWriter out = response.getWriter();
				out.write(JacksonUtils.writeValueAsString(result));
				out.flush();
				out.close();
				return;
			}
		}
		Admin admin = redisCache.getCacheObject(username);
		if (Objects.isNull(admin)) {
			throw new BizException("token已失效,请重新登录!");
		}
		if ((admin.getExpiration().getTime() - new Date().getTime()) < 30 * 60 * 1000L) {
			admin.setExpiration(new Date(System.currentTimeMillis() + expireTime));
			redisCache.setCacheObject(username, admin);
		}
		assert username != null;
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(token);
		filterChain.doFilter(request, response);
	}
}