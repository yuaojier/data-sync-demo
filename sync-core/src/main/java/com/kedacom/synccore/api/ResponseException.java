package com.kedacom.synccore.api;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ResponseException extends RuntimeException implements IHttpResponse {
    private static final long serialVersionUID = 5357488575558036849L;

    @NonNull
    private String code;

    @NonNull
    private Object data;

    private String msg;

    public ResponseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseException(String code, Object data, String msg) {
        super(msg);
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}