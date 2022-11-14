package com.xs.handler;

import com.xs.common.Result;
import com.xs.exception.BizException;
import com.xs.exception.NotFoundException;
import com.xs.exception.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 对Controller层全局异常处理
 * 捕获异常后，返回json数据类型
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 捕获自定义的404异常
	 * @param request 请求
	 * @param e       自定义抛出的异常信息
	 */
	@ExceptionHandler(NotFoundException.class)
	public Result notFoundExceptionHandler(HttpServletRequest request, NotFoundException e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(404, "请求url不存在");
	}

	/**
	 * 捕获自定义的持久化异常
	 * @param request 请求
	 * @param e       自定义抛出的异常信息
	 */
	@ExceptionHandler(PersistenceException.class)
	public Result persistenceExceptionHandler(HttpServletRequest request, PersistenceException e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(500, "持久化异常");
	}

	/**
	 * 捕获自定义的登录失败异常
	 * @param request 请求
	 * @param e       自定义抛出的异常信息
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public Result usernameNotFoundExceptionHandler(HttpServletRequest request, UsernameNotFoundException e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(401, "登录失败");
	}

	/**
	 * 捕获其它异常
	 * @param request 请求
	 * @param e       异常信息
	 */
	@ExceptionHandler(Exception.class)
	public Result exceptionHandler(HttpServletRequest request, Exception e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(500, "系统异常");
	}

	/**
	 * 捕获其它异常
	 * @param request 请求
	 * @param e       异常信息
	 */
	@ExceptionHandler(BizException.class)
	public Result BizExceptionHandler(HttpServletRequest request, Exception e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(500, "业务异常");
	}

	/**
	 * 捕获其它异常
	 * @param request 请求
	 * @param e       异常信息
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public Result BadCredentialsExceptionHandler(HttpServletRequest request, Exception e) {
		logger.error("Request URL : {}, Exception :", request.getRequestURL(), e);
		return Result.create(500, "用户名或密码错误");
	}
}
