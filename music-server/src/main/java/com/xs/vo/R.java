package com.xs.vo;

import com.xs.enums.StatusCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.xs.enums.StatusCodeEnum.FAIL;
import static com.xs.enums.StatusCodeEnum.SUCCESS;

/**
 * 接口返回类
 */
@Data
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回状态
     */
    private Boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> R<T> ok() {
        return restResult(true, null, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> R<T> ok(T data) {
        return restResult(true, data, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static <T> R<T> ok(T data, String message) {
        return restResult(true, data, SUCCESS.getCode(), message);
    }

    public static <T> R<T> fail() {
        return restResult(false, null, FAIL.getCode(), FAIL.getDesc());
    }

    public static <T> R<T> fail(StatusCodeEnum statusCodeEnum) {
        return restResult(false, null, statusCodeEnum.getCode(), statusCodeEnum.getDesc());
    }

    public static <T> R<T> fail(String message) {
        return restResult(message);
    }

    public static <T> R<T> fail(T data) {
        return restResult(false, data, FAIL.getCode(), FAIL.getDesc());
    }

    public static <T> R<T> fail(T data, String message) {
        return restResult(false, data, FAIL.getCode(), message);
    }

    public static <T> R<T> fail(Integer code, String message) {
        return restResult(false, null, code, message);
    }

    private static <T> R<T> restResult(String message) {
        R<T> apiResult = new R<>();
        apiResult.setFlag(false);
        apiResult.setCode(FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    private static <T> R<T> restResult(Boolean flag, T data, Integer code, String message) {
        R<T> apiResult = new R<>();
        apiResult.setFlag(flag);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

}
