package com.atlas.core.web;

/**
 * 自定义返回结果
 *
 *
 */
public class AcResult<T> {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private T content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public AcResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AcResult(int code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public AcResult(X status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public AcResult(X status, T content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static AcResult ok(Object content) {
        return new AcResult(X.SUCCESS, content);
    }

    public static AcResult ok() {
        return new AcResult(X.SUCCESS);
    }

    public static AcResult error() {
        return new AcResult(X.ERROR);
    }

    public static AcResult error(String message) {
        return new AcResult(X.ERROR.getCode(), message);
    }

    @Override
    public String toString() {
        return "AcResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
