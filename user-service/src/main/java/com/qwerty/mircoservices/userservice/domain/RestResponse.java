package com.qwerty.mircoservices.userservice.domain;

public class RestResponse {
    String msg;

    public RestResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}