package com.atlas.business;

/**
 * Created by renfei on 17/5/10.
 */
public abstract class Constant {

    /**
     * 业务状态码
     */
    enum StatusCode{
        USER_NO_SEARCH(1001, "用户未找到");

        int code;
        String message;

        StatusCode(int code, String message){
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

}
