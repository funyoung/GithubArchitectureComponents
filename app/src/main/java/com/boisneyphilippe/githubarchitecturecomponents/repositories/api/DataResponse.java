package com.boisneyphilippe.githubarchitecturecomponents.repositories.api;

import com.google.gson.Gson;

/**
 * @author yangfeng
 */
public class DataResponse <T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> T from(String sampleJson, Class<? extends DataResponse<T>> aClass) {
        return new Gson().fromJson(sampleJson, aClass).getData();
    }
}
