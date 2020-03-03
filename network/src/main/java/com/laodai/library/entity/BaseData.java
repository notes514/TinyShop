package com.laodai.library.entity;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:36 2020-01-26
 * @ Description：返回数据基类
 * @ Modified By：
 * @Version: ：1.0
 */
public class BaseData<T> {
    //错误码
    private int code;
    //错误描述
    private String msg;
    //返回的数据
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
}
