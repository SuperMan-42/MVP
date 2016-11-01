package com.hpw.mvpframe.data.entity;

public class CoreErrorResponse {

    private int code;
    private String error;

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "CoreErrorResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}

