package com.hangzhou.hezi.vendingmachine.bean;

/**
 * Created by Administrator on 2019/3/25.
 */

public class CodeBean {

    /**
     * code : 10000
     * message : 成功
     * data : http://weixin.qq.com/q/02Nh5TkWaafID10000g07i
     */

    private int code;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
