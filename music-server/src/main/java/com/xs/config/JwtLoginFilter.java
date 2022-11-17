package com.xs.config;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.exception.BadRequestException;
import com.xs.util.JacksonUtils;
import com.xs.util.JwtUtils;
import com.xs.util.RedisCache;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT登录过滤器
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    ThreadLocal<String> currentUsername = new ThreadLocal<>();

    private static final long expireTime = 7 * 24 * 60 * 60 * 1000L;

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
            Admin user = JacksonUtils.readValue(request.getInputStream(), Admin.class);
            assert user != null;
            currentUsername.set(user.getUsername());
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
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
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        RedisCache redisCache = applicationContext.getBean(RedisCache.class);
        String jwt = JwtUtils.generateToken(authResult.getName(), authResult.getAuthorities());
        response.setContentType("application/json;charset=utf-8");
        Admin user = (Admin) authResult.getPrincipal();
        user.setPassword(null);
        Map<String, Object> map = new HashMap<>(4);
        map.put("user", user);
        map.put("token", jwt);
        user.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        redisCache.setCacheObject(user.getUsername(), user);
        Result result = Result.ok("登录成功", map);
        PrintWriter out = response.getWriter();
        out.write(JacksonUtils.writeValueAsString(result));
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) throws IOException {
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
