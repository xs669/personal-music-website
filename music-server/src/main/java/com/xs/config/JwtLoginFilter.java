package com.xs.config;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.dto.LoginInfoDto;
import com.xs.exception.BadRequestException;
import com.xs.util.JacksonUtils;
import com.xs.util.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT登录过滤器
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

	ThreadLocal<String> currentUsername = new ThreadLocal<>();

	protected JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new BadRequestException("请求方法错误");
			}
			LoginInfoDto loginInfoDto = JacksonUtils.readValue(request.getInputStream(), LoginInfoDto.class);
			assert loginInfoDto != null;
			currentUsername.set(loginInfoDto.getUsername());
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginInfoDto.getUsername(), loginInfoDto.getPassword()));
		} catch (BadRequestException exception) {
			response.setContentType("application/json;charset=utf-8");
			Result result = Result.create(400, "非法请求");
			PrintWriter out = response.getWriter();
			out.write(JacksonUtils.writeValueAsString(result));
			out.flush();
			out.close();
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
		String jwt = JwtUtils.generateToken(authResult.getName(), authResult.getAuthorities());
		response.setContentType("application/json;charset=utf-8");
		Admin admin = (Admin) authResult.getPrincipal();
		admin.setPassword(null);
		Map<String, Object> map = new HashMap<>(4);
		map.put("user", admin);
		map.put("token", jwt);
		Result result = Result.ok("登录成功", map);
		PrintWriter out = response.getWriter();
		out.write(JacksonUtils.writeValueAsString(result));
		out.flush();
		out.close();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String msg = exception.getMessage();
		//登录不成功时，会抛出对应的异常
		if (exception instanceof LockedException) {
			msg = "账号被锁定";
		} else if (exception instanceof CredentialsExpiredException) {
			msg = "密码过期";
		} else if (exception instanceof AccountExpiredException) {
			msg = "账号过期";
		} else if (exception instanceof DisabledException) {
			msg = "账号被禁用";
		} else if (exception instanceof BadCredentialsException) {
			msg = "用户名或密码错误";
		}
		PrintWriter out = response.getWriter();
		out.write(JacksonUtils.writeValueAsString(Result.create(401, msg)));
		out.flush();
		out.close();
	}
}