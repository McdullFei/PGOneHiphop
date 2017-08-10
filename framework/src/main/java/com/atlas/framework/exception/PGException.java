package com.atlas.framework.exception;

/**
 * 处理异常
 */
public class PGException extends RuntimeException{
    protected int errorCode = -1;
    protected String errorMessage = "";

    public PGException(Throwable t) {
        super(t);
    }

    public PGException(String s, Exception e) {
        super(s, e);
        this.errorMessage = e.getMessage();
    }

    public PGException(String s) {
        super(s);
        this.errorMessage = s;
    }

    public PGException(int errorCode, String s) {
        super(s);
        this.errorCode = errorCode;
        this.errorMessage = s;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
