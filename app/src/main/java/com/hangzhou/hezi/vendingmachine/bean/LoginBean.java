package com.hangzhou.hezi.vendingmachine.bean;

/**
 * Created by Administrator on 2019/3/21.
 */

public class LoginBean {

    /**
     * code : 10000
     * message : 成功
     * data : null
     */

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
