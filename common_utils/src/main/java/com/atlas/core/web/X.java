package com.atlas.core.web;

/**
 * 状态码base
 */
public enum X {

    ERROR(-1, "系统异常"),
    SUCCESS(0, "成功");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    X(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
