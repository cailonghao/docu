package com.docu.gateway.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Result<T> implements Serializable {


    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> succ(String msg, T data) {
        return new Result<T>(200, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(500, msg, null);
    }
}
